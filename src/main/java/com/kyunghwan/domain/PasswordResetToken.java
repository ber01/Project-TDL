package com.kyunghwan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter @Setter
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Date expiryDate;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    public void setExpiryDate(int minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);
        this.expiryDate = calendar.getTime();
    }

    public boolean isExpired(){
        return new Date().after(this.expiryDate);
    }
}
