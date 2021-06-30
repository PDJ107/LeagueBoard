package controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import service.RiotApiService;

@Controller
@RequestMapping(value = "/riotapi")
public class RiotApiController {
    @Autowired
    private RiotApiService riotApiService;

    @ResponseBody
    @RequestMapping(value = "/summoner/{summoner_name}", method = RequestMethod.GET)
    @ApiOperation(value = "소환사 정보 조회")
    public ResponseEntity getSummonerInfo(@PathVariable String summoner_name) throws Exception{
        return new ResponseEntity(riotApiService.getSummonerInfo(summoner_name), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/league/{encrypted_summoner_name}", method = RequestMethod.GET)
    @ApiOperation(value = "리그 정보 조회")
    public ResponseEntity getLeagueInfo(@PathVariable String encrypted_summoner_name) throws Exception {
        return new ResponseEntity(riotApiService.getLeagueInfo(encrypted_summoner_name), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/spectator/{encrypted_summoner_name}", method = RequestMethod.GET)
    @ApiOperation(value = "게임 중인지 여부 체크")
    public ResponseEntity getSpectatorInfo(@PathVariable String encrypted_summoner_name) throws Exception {
        //return new ResponseEntity(riotApiService.getSpectatorInfo(encrypted_summoner_name), HttpStatus.OK);
        return new ResponseEntity(riotApiService.isPlaying(encrypted_summoner_name), HttpStatus.OK);
    }
}
