package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTests {

    @Test
    public void CardNumberIsCreated(){
        String cardNumber= CardUtils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));

    }
    @Test
    public void CvvNumberIsCreated(){
        Integer cvvNumber= CardUtils.getCvvN();
        assertThat(cvvNumber.toString(),is(not(emptyOrNullString())));

    }


}
