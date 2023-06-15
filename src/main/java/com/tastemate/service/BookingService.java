package com.tastemate.service;

import com.tastemate.domain.BookingVO;
import com.tastemate.mapper.BookingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {


    @Autowired
    private BookingMapper bookingMapper;

    public List<BookingVO> bookingList() {
        List<BookingVO> bookingList = bookingMapper.bookingList();
        return bookingList;
    }

    public int bookingToPay(BookingVO bookingVO) {
        int result = bookingMapper.bookingInsert(bookingVO);
        return result;
    }


    public BookingVO bookingToPayShow(int bookingIdx) {
      BookingVO bookingVO = bookingMapper.bookingToPay(bookingIdx);

      return bookingVO;
    };

    public int bookingTableCheck(BookingVO bookingVO) {
        int result = bookingMapper.bookingTableCheck(bookingVO);
        return result;
    }

    public int bookingPayAndStarComplete(int bookingIdx) {
        int result = bookingMapper.bookingPayAndStarComplete(bookingIdx);

        return result;
    }
    public int bookingPayCancel(int bookingIdx) {
        int result = bookingMapper.bookingPayCancel(bookingIdx);

        return result;
    }



}
