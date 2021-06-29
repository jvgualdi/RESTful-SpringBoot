package com.course.restspring.services;

import com.course.restspring.pontointeligente.entities.Employee;
import com.course.restspring.pontointeligente.entities.Release;
import com.course.restspring.pontointeligente.repositories.ReleaseRepository;
import com.course.restspring.pontointeligente.services.ReleaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ReleaseServiceTest {

    @MockBean
    private ReleaseRepository releaseRepository;

    @Autowired
    private ReleaseService releaseService;

    @Before
    public void setUp(){
        //BDDMockito.given(this.releaseRepository.findByEmployeeId(Mockito.anyLong())).willReturn(new List<Release>());
        BDDMockito.given(this.releaseRepository.findById(Mockito.anyLong())).willReturn(Optional.of(new Release()));
        BDDMockito.given(this.releaseRepository.save(Mockito.any(Release.class))).willReturn(new Release());
    }

    @Test
    public void testFindById(){
        Optional<Release> release = releaseService.findById(new Long(1));
        assertTrue(release.isPresent());
    }

    @Test
    public void testInsertOnDBTest(){
        Release release = releaseService.insertOnDB(new Release());
        assertNotNull(release);
    }

    @Test
    public void testFindByEmployeeId(){

    }

}
