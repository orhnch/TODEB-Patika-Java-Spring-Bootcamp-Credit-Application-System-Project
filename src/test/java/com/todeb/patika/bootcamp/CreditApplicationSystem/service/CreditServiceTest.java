package com.todeb.patika.bootcamp.CreditApplicationSystem.service;

import com.todeb.patika.bootcamp.CreditApplicationSystem.model.entity.Credit;
import com.todeb.patika.bootcamp.CreditApplicationSystem.model.enums.CreditStatus;
import com.todeb.patika.bootcamp.CreditApplicationSystem.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreditServiceTest {

    @Mock
    private CreditRepository creditRepository;


    @InjectMocks
    private CreditService creditService;

    @Test
    void getAllCredits() {
        //init step
        List<Credit> expCreditList = getSampleTestCredits();

        //stub - when step
        when(creditRepository.findAll()).thenReturn(expCreditList);

        //then - validate step
        List<Credit> actualCreditList = creditService.getAllCredits();

        assertEquals(expCreditList.size(), actualCreditList.size());

        expCreditList = expCreditList.stream().sorted(getCreditComparator()).collect(Collectors.toList());
        actualCreditList = actualCreditList.stream().sorted(getCreditComparator()).collect(Collectors.toList());
        for (int i = 0; i < expCreditList.size(); i++) {
            Credit currExpectedCredit = expCreditList.get(i);
            Credit currActualCredit = actualCreditList.get(i);
            assertEquals(currExpectedCredit.getId(), currActualCredit.getId());
            assertEquals(currExpectedCredit.getCreditLimit(), currActualCredit.getCreditLimit());
            assertEquals(currExpectedCredit.getStatus(), currActualCredit.getStatus());
        }
    }

    @Test
    void delete() {
        //init step
        Credit credit = getSampleTestCredits().get(0);


        //stub - when step
        when(creditRepository.findById(credit.getId())).thenReturn(Optional.of(credit));

        //then - validate step
        creditService.delete(credit.getId());
        verify(creditRepository).deleteById(credit.getId());
    }

    private List<Credit> getSampleTestCredits() {
        List<Credit> sampleList = new ArrayList<>();
        Credit credit = new Credit(1L, 10000, CreditStatus.APPROVED, null, null, null);
        Credit credit2 = new Credit(2L, 20000, CreditStatus.APPROVED, null, null, null);
        Credit credit3 = new Credit(3L, 20000, CreditStatus.APPROVED, null, null, null);
        sampleList.add(credit);
        sampleList.add(credit2);
        sampleList.add(credit3);
        return sampleList;
    }


    private Comparator<Credit> getCreditComparator() {
        return (o1, o2) -> {
            if (o1.getId() - o2.getId() < 0)
                return -1;
            if (o1.getId() - o2.getId() == 0)
                return 0;
            return 1;
        };
    }
}