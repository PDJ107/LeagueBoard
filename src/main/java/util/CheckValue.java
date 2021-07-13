package util;

import domain.Report;
import domain.ReportCode;
import domain.Search;
import exception.BoardException;
import exception.ErrorCode;
import exception.UserException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheckValue {
    public void checkAccount(String account) throws Exception{
        if(account == null) throw new UserException(ErrorCode.Account_Is_Null);
        else if(account.length() < 1 || account.length() > 20)
            throw new UserException(ErrorCode.Account_Not_Valid);
    }

    public void checkPassword(String password) throws Exception{
        if(password == null) throw new UserException(ErrorCode.Password_Is_Null);
        else if(password.length() < 4 || password.length() > 20)
            throw new UserException(ErrorCode.Password_Not_Valid);
    }

    public void checkSummonerName(String summoner_name) throws Exception{
        if(summoner_name == null) throw new UserException(ErrorCode.Summoner_Name_Is_Null);
        else if(summoner_name.length() > 20)
            throw new UserException(ErrorCode.Summoner_Name_Not_Valid);
    }

    public void checkTitle(String title) throws Exception{
        if(title == null) throw new BoardException(ErrorCode.Title_Is_Null);
        else if(title.length() < 1 || title.length() > 20)
            throw new BoardException(ErrorCode.Title_Not_Valid);
    }
    public void checkContents(String contents) throws Exception{
        if(contents == null) throw new BoardException(ErrorCode.Contents_Is_Null);
        else if(contents.length() < 1 || contents.length() > 150)
            throw new BoardException(ErrorCode.Contents_Not_Valid);
    }

    public void checkComment(String contents) throws Exception{
        if(contents == null) throw new BoardException(ErrorCode.Comment_Is_Null);
        else if(contents.length() < 1 || contents.length() > 50)
            throw new BoardException(ErrorCode.Comment_Not_Valid);
    }

    public void checkSearch(Search search) throws Exception{
        if(search.getPage() == null) throw new BoardException(ErrorCode.PageNum_Is_Null);
        else if(search.getCount() < 1 || search.getCount() > 20) throw new BoardException(ErrorCode.PageCount_Not_Valid);
        else if(search.getPage() < 1) throw new BoardException(ErrorCode.PageNum_Not_Valid);
    }

    public void reportCheck(Report report, List<ReportCode> reportCodes) {
        if(report.getPerpetrator_id() == null) throw new UserException(ErrorCode.User_Id_Is_Null);
        else if(report.getCode() == null) throw new UserException(ErrorCode.Report_Code_Is_Null);

        Boolean isValid = false;
        for(int i = 0; i < reportCodes.size(); ++i)
            if(report.getCode() == reportCodes.get(i).getCode()) isValid = true;
        if(!isValid) throw new UserException(ErrorCode.Report_Code_Not_Valid);
    }
}
