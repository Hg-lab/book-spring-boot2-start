package com.community.rest;

import com.community.rest.domain.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase // H2에서 테스트
public class BoardEventTest {

    private TestRestTemplate testRestTemplate = new TestRestTemplate("hank", "test");
    private final String TEST_BOARD_LOCAL_URL = "http://127.0.0.1:8081/api/boards";

    @Test
    public void Created_Date_Test() {
        Board createdBoard = createBoard();
        assertNotNull(createdBoard.getCreatedDate());
    }

    @Test
    public void Updated_Date_Test() {
        Board createdBoard = createBoard();
        Board updatedBoard = updateBoard(createdBoard);
        assertNotNull(updatedBoard.getCreatedDate());
        assertNotNull(updatedBoard.getUpdatedDate());
    }

    private Board createBoard() {
        Board board = Board.builder().title("저장 이벤트 테스트").build();
        return testRestTemplate.postForObject(TEST_BOARD_LOCAL_URL, board, Board.class);
    }

    private Board updateBoard(Board createdBoard) {
        String updateUri = TEST_BOARD_LOCAL_URL + "/1";
        testRestTemplate.put(updateUri, createdBoard);
        return testRestTemplate.getForObject(updateUri, Board.class);
    }
}