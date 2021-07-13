package exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    User_Not_Found("USER_01", "존재하지 않는 유저입니다.", HttpStatus.NOT_FOUND),
    User_Invalid_Request("USER_02", "account 또는 password가 틀렸습니다.", HttpStatus.BAD_REQUEST),
    Account_Already_Exists("USER_03", "이미 존재하는 Account 입니다.", HttpStatus.BAD_REQUEST),
    Report_Invalid_Request("USER_04", "자신은 신고할 수 없습니다.", HttpStatus.BAD_REQUEST),
    Report_Code_Is_Null("USER_05", "유저 신고 코드가 Null입니다.", HttpStatus.BAD_REQUEST),
    Report_Code_Not_Valid("USER_06", "잘못된 report code 입니다.", HttpStatus.BAD_REQUEST),
    Report_Same_User("USER_07", "같은 유저를 2번 신고할 수 없습니다.", HttpStatus.FORBIDDEN),
    Account_Is_Null("USER_08", "account가 Null입니다.", HttpStatus.BAD_REQUEST),
    Password_Is_Null("USER_09", "password가 Null입니다.", HttpStatus.BAD_REQUEST),
    User_Id_Is_Null("USER_10", "User id가 Null입니다.", HttpStatus.BAD_REQUEST),

    Account_Not_Valid("USER_11", "account의 길이는 1 ~ 20자 입니다.", HttpStatus.BAD_REQUEST),
    Password_Not_Valid("USER_12", "password의 길이는 4 ~ 20자 입니다.", HttpStatus.BAD_REQUEST),


    Summoner_Not_Found("RIOT_API_01", "존재하지 않는 소환사 이름입니다.", HttpStatus.NOT_FOUND),
    RiotApi_Request_Failed("RIOT_API_02", "Riot API 요청에 실패했습니다.", HttpStatus.BAD_REQUEST),
    Summoner_Name_Is_Null("RIOT_API_03", "소환사 이름이 Null입니다.", HttpStatus.BAD_REQUEST),

    Summoner_Name_Not_Valid("RIOT_API_04", "소환사 이름은 20자보다 작습니다. (3 ~ 16)", HttpStatus.BAD_REQUEST),


    Token_Is_Null("TOKEN_01", "토큰이 Null 입니다.", HttpStatus.UNAUTHORIZED),
    Expired_Token("TOKEN_02", "토큰이 만료됐습니다.", HttpStatus.UNAUTHORIZED),
    Invalid_Token("TOKEN_03", "토큰이 잘못됐습니다.", HttpStatus.UNAUTHORIZED),
    Invalid_Token_Bearer("TOKEN_04", "토큰이 Bearer로 시작하지 않습니다.", HttpStatus.UNAUTHORIZED),
    Invalid_Token_User_Id("TOKEN_05", "토큰의 User Id에 해당하는 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    Logged_Out_Token("TOKEN_06", "로그아웃된 토큰입니다.", HttpStatus.UNAUTHORIZED),


    Party_Already_Exists("BOARD_01", "파티가 이미 존재합니다.", HttpStatus.BAD_REQUEST),
    Party_Not_Exists("BOARD_02", "참가중인 파티가 없습니다.", HttpStatus.BAD_REQUEST),
    Party_Is_Full("BOARD_03", "인원이 꽉 차 참가할 수 없습니다.", HttpStatus.FORBIDDEN),
    Party_Invalid_Request("BOARD_04", "Admin은 파티에서 나갈 수 없습니다. (모집 글(파티) 삭제만 가능)", HttpStatus.BAD_REQUEST),
    Board_Not_Found("BOARD_05", "모집 글(파티)을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    Comment_Not_Found("BOARD_06", "댓글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    Comment_Invalid_Access("BOARD_07", "자신이 작성한 댓글이 아닙니다.", HttpStatus.FORBIDDEN),
    Comment_Id_Is_Null("BOARD_08", "댓글 id가 Null입니다.", HttpStatus.BAD_REQUEST),
    Member_Not_Found("BOARD_09", "해당 멤버를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    Board_Id_Is_Null("BOARD_10", "Board Id가 Null입니다.", HttpStatus.BAD_REQUEST),
    Title_Is_Null("BOARD_11", "제목이 Null입니다.", HttpStatus.BAD_REQUEST),
    Contents_Is_Null("BOARD_12", "내용이 Null입니다.", HttpStatus.BAD_REQUEST),
    Comment_Is_Null("BOARD_13", "댓글이 Null입니다.", HttpStatus.BAD_REQUEST),

    Title_Not_Valid("BOARD_13", "제목의 길이는 1 ~ 20자 입니다.", HttpStatus.BAD_REQUEST),
    Contents_Not_Valid("BOARD_14", "내용의 길이는 1 ~ 150자 입니다.", HttpStatus.BAD_REQUEST),
    Comment_Not_Valid("BOARD_15", "댓글의 길이는 1 ~ 50자 입니다.", HttpStatus.BAD_REQUEST),
    PageNum_Not_Valid("BOARD_16", "페이지는 0보다 커야합니다.", HttpStatus.BAD_REQUEST),
    PageCount_Not_Valid("BOARD_17", "게시글의 수는 1 ~ 20입니다.", HttpStatus.BAD_REQUEST),
    PageNum_Is_Null("BOARD_18", "pageNum이 Null입니다.", HttpStatus.BAD_REQUEST)
    ;

    private String code;
    private String message;
    private HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
