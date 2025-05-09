package com.e_learning.Sikshyalaya.controllers;

import com.e_learning.Sikshyalaya.dtos.*;
import com.e_learning.Sikshyalaya.entities.*;
import com.e_learning.Sikshyalaya.repositories.*;
import com.e_learning.Sikshyalaya.service.CourseService;
import com.e_learning.Sikshyalaya.service.EmailService;
import com.e_learning.Sikshyalaya.service.EnrollmentService;
import com.e_learning.Sikshyalaya.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Slf4j
@RestController
public class UserController {

    private final CourseService courseService;

    private final UserService userService;

    private final EnrollmentRepository enrollmentRepository;

    private final EnrollmentService enrollmentService;

    private final LectureRepository lectureRepository;

    private final CommentRepository commentRepository;

    private final UserProgressRepository userProgressRepository;

    private SectionRepository sectionRepository;

    private CommentReplyRepository commentReplyRepository;
    private EmailService emailService;
    @Autowired
    private PaymentRepository paymentRepository;

   // @Autowired
   // private VideoFeedbackRepository videoFeedbackRepository;

    @Autowired
    private MessageRepository messageRepository;

    public UserController(
            CourseService courseService,
            UserService userService,
            EnrollmentRepository enrollmentRepository,
            EnrollmentService enrollmentService,
            LectureRepository lectureRepository,
            CommentRepository commentRepository,
            UserProgressRepository userProgressRepository,
            SectionRepository sectionRepository,
            CommentReplyRepository commentReplyRepository,
            EmailService emailService) {
        this.userService = userService;
        this.enrollmentRepository = enrollmentRepository;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
        this.lectureRepository = lectureRepository;
        this.commentRepository = commentRepository;
        this.userProgressRepository = userProgressRepository;
        this.sectionRepository = sectionRepository;
        this.commentReplyRepository = commentReplyRepository;
        this.emailService = emailService;
    }

    @PostMapping("/enrollment/{courseId}")
    public ResponseEntity<?> enrollCourse(@PathVariable Integer courseId) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userName = authentication.getName();
        Optional<User> byUserName = userService.getByUserName(userName);
        User user = byUserName.orElseThrow(() -> new RuntimeException("User not found"));
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        Enrollment enrollment1 = enrollments.stream().filter(enrollment ->
                Objects.equals(enrollment.getCourse().getCourseID(), courseId) && enrollment.getUser().getUserName().equals(userName)).findFirst().orElse(null);
        if (enrollment1 != (null)) {
            return new ResponseEntity<>("Course already enrolled", HttpStatus.BAD_REQUEST);
        }
        Course course = courseService.findById(courseId);
        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
        Payment payment = new Payment();
        payment.setCourse(course);
        payment.setUser(user);
        payment.setAmount(course.getCoursePrice());
        paymentRepository.save(payment);
        // Send enrollment confirmation email
        try {
            emailService.sendEnrollmentConfirmation(user.getEmail(), course.getCourseName());
        } catch (Exception e) {
            // Log the error but don't fail the enrollment
            log.error("Failed to send enrollment email", e);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/enrollment")
    public List<CourseResponseDto> getEnrolledCourseByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> byUserName = userService.getByUserName(userName);
        User user = byUserName.orElseThrow(() -> new RuntimeException("User not found"));
        List<Enrollment> enrollments = user.getEnrollments();
        List<CourseResponseDto> courseResponse = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            int totalLecture = enrollment.getCourse().getSections().stream()
                    .mapToInt(section -> section.getLectures().size())
                    .sum();
            CourseResponseDto courseResponseDto = new CourseResponseDto(enrollment.getCourse());
            courseResponseDto.setTotalLectures(totalLecture);
            int totalFinished = userProgressRepository.findAll().stream().filter(userProgress -> userProgress.getLecture().getSection().getCourse().getCourseID().equals(enrollment.getCourse().getCourseID())).toList().size();
            courseResponseDto.setTotalFinished(totalFinished);
            courseResponseDto.setPercentageFinished(((double) totalFinished / totalLecture) * 100);
            courseResponse.add(courseResponseDto);
        }
        return courseResponse;
    }

    @PreAuthorize("@enrollmentService.isUserEnrolled(#courseId,authentication.name)")
    @GetMapping("/enrollment/course/{courseId}")
    public Course getCourseById(@PathVariable Integer courseId) {
        return courseService.findById(courseId);
    }

    @GetMapping("/enrollment/courses")
    public List<Course> getEnrolledCourses() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return enrollmentRepository.findAll().
                stream().
                filter(enrollment -> enrollment.getUser().getUserName().equals(userName))
                .map(Enrollment::getCourse)
                .toList();
    }


    @PostMapping("/message/course/{courseId}")
    public String saveMessage(@PathVariable Integer courseId, @RequestBody RequestMessage requestMessage) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Course course = courseService.findById(courseId);
        User user = userService.getByUserName(name).orElseThrow(() -> new RuntimeException("User not found"));
        Message message = new Message();
        message.setMessage(requestMessage.getMessage());
        message.setUser(user);
        message.setDate(new Date());
        message.setCourse(course);
        messageRepository.save(message);
        return "Success";
    }


    @PostMapping("/comment/{lectureId}")
    public ResponseEntity<?> saveComment(@RequestBody CommentRequestDto commentRequestDto, @PathVariable Integer lectureId) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUserName(name).orElseThrow(() -> new RuntimeException("User not found"));

        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new RuntimeException("Lecture not found"));
        System.out.println(STR."Comment: \{commentRequestDto.getComment()}");
        Comment comment = new Comment();
        comment.setComment(commentRequestDto.getComment());
        comment.setUser(user);
        comment.setLecture(lecture);
        comment.setDate(new Date());
        commentRepository.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/comment/{lectureId}")
    public List<CommentResponseDto> getComment(@PathVariable Integer lectureId) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getByUserName(name).orElseThrow(() -> new RuntimeException("User not found"));
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new RuntimeException("Lecture not found"));
        List<Comment> comments = lecture.getComments();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            List<CommentReplyResponseDto> list = comment.getCommentReplies().stream().map(commentReply -> new CommentReplyResponseDto(commentReply)).toList();
            commentResponseDto.setCommentReplies(list);
            commentResponseDtos.add(commentResponseDto);

        }
        return commentResponseDtos;
    }


    @PostMapping("/progress/{lectureId}")
    public ResponseEntity<?> trackProgress(@PathVariable Integer lectureId) {
        String userName = getUserName();
        User user = userService.getByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new RuntimeException("Lecture not found"));
        Optional<UserProgress> first = userProgressRepository.findAll().stream().filter(userProgress -> userProgress.getUser().getUserName().equals(userName) && userProgress.getLecture().getId().equals(lectureId)).findFirst();
        if (first.isPresent()) {
            throw new RuntimeException("User progress already exists");
        }
        UserProgress userProgress = new UserProgress();
        userProgress.setUser(user);
        userProgress.setLecture(lecture);
        userProgressRepository.save(userProgress);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @GetMapping("/sections/{sectionId}/lectures")
    public List<LectureReponseDto> getLectures(@PathVariable Integer sectionId) {
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new RuntimeException("Section not found"));
        String userName = getUserName();
        List<UserProgress> userProgresses = userProgressRepository.findAll();
        List<LectureReponseDto> reponseDtoList = new ArrayList<>();
        for (Lecture lecture : section.getLectures()) {
            LectureReponseDto lectureReponseDto = new LectureReponseDto(lecture);
            boolean isCompleted = userProgresses.stream().anyMatch(userProgress -> userProgress.getUser().getUserName().equals(userName) && userProgress.getLecture().getId().equals(lecture.getId()));
            System.out.println(isCompleted);
            lectureReponseDto.setCompleted(isCompleted);
            reponseDtoList.add(lectureReponseDto);
        }

        return reponseDtoList;
    }

    @PostMapping("/lectures/{lectureId}/markcompleted")
    public ResponseEntity<?> markLectureAsCompleted(@PathVariable Integer lectureId) {
        String userName = getUserName();
        User user = userService.getByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new RuntimeException("Lecture not found"));
        boolean b = userProgressRepository.findAll().stream().anyMatch(userProgress -> userProgress.getUser().getUserName().equals(userName) && userProgress.getLecture().getId().equals(lectureId));
        if (b) {
            throw new RuntimeException("User already completed that course");
        }
        UserProgress userProgress = new UserProgress();
        userProgress.setLecture(lecture);
        userProgress.setUser(user);
        userProgressRepository.save(userProgress);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/comments/{commentId}/reply")
    public ResponseEntity<?> doCommentReply(@PathVariable Integer commentId, @RequestBody CommentReplyRequestDto commentReplyRequestDto) {
        String userName = getUserName();
        User user = userService.getByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
        CommentReply commentReply = new CommentReply();
        commentReply.setReply(commentReplyRequestDto.getReply());
        commentReply.setDate(new Date());
        commentReply.setUser(user);
        commentReply.setComment(comment);
        commentReplyRepository.save(commentReply);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping("/feedback/video/{lectureId}")
//    public ResponseEntity<?> addVideoFeedback(@PathVariable Integer lectureId, @RequestBody VideoFeedbackRepositoryRequestDto videoFeedbackRequestDto) {
//        String userName = getUserName();
//        User user = userService.getByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
//        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new RuntimeException("Lecture not found"));
//
//        VideoFeedback videoFeedback = new VideoFeedback();
//        videoFeedback.setFeedback(videoFeedbackRequestDto.getFeedback());
//        videoFeedback.setVideoUrl(videoFeedbackRequestDto.getVideoUrl());
//        videoFeedback.setDate(new Date());
//        videoFeedback.setUser(user);
//        videoFeedback.setLecture(lecture);
//
//        videoFeedbackRepository.save(videoFeedback);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


}