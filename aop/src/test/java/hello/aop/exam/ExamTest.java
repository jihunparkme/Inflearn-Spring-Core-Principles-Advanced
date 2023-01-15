package hello.aop.exam;

import hello.aop.exam.aop.RetryAspect;
import hello.aop.exam.aop.TraceAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import({TraceAspect.class, RetryAspect.class})
@SpringBootTest
public class ExamTest {

    @Autowired
    ExamService examService;

    /**
     * [trace] void hello.aop.exam.ExamService.request(String) args=[data0]
     * [trace] String hello.aop.exam.ExamRepository.save(String) args=[data0]
     * [retry] String hello.aop.exam.ExamRepository.save(String) retry=@hello.aop.exam.annotation.Retry(4)
     * [retry] try count=1/4
     *
     * [trace] void hello.aop.exam.ExamService.request(String) args=[data1]
     * [trace] String hello.aop.exam.ExamRepository.save(String) args=[data1]
     * [retry] String hello.aop.exam.ExamRepository.save(String) retry=@hello.aop.exam.annotation.Retry(4)
     * [retry] try count=1/4
     *
     * [trace] void hello.aop.exam.ExamService.request(String) args=[data2]
     * [trace] String hello.aop.exam.ExamRepository.save(String) args=[data2]
     * [retry] String hello.aop.exam.ExamRepository.save(String) retry=@hello.aop.exam.annotation.Retry(4)
     * [retry] try count=1/4
     *
     * [trace] void hello.aop.exam.ExamService.request(String) args=[data3]
     * [trace] String hello.aop.exam.ExamRepository.save(String) args=[data3]
     * [retry] String hello.aop.exam.ExamRepository.save(String) retry=@hello.aop.exam.annotation.Retry(4)
     * [retry] try count=1/4
     *
     * [trace] void hello.aop.exam.ExamService.request(String) args=[data4]
     * [trace] String hello.aop.exam.ExamRepository.save(String) args=[data4]
     * [retry] String hello.aop.exam.ExamRepository.save(String) retry=@hello.aop.exam.annotation.Retry(4)
     * [retry] try count=1/4
     *
     * [retry] try count=2/4 -> retry 시도
     */
    @Test
    void test() {
        for (int i = 0; i < 5; i++) {
            examService.request("data" + i);
        }
    }
}
