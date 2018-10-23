package cn.hyperchain.application.controller;

import cn.hyperchain.application.common.response.BaseResult;
import cn.hyperchain.application.contract.invoke.ContractInvoke;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 合约操作
 *
 * @author LauItachi
 * @date 2018/10/23
 */
@RestController
public class ContractController {

    @Autowired
    private ContractInvoke contractInvoke;


    /**
     * 新建投票
     *
     * @param content 投票标题
     */
    @ApiOperation(value = "新建投票", notes = "投票系统")
    @RequestMapping(value = "/vote/post", method = RequestMethod.POST)
    public BaseResult postVote(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "投票内容") @RequestParam String content) {
        return contractInvoke.postVote(token, invokeAddress, content);
    }

    /**
     * 参与投票
     *
     * @param voteId 投票ID
     * @param choice 投票标题
     */
    @ApiOperation(value = "参与投票", notes = "投票系统")
    @RequestMapping(value = "/vote/voting", method = RequestMethod.POST)
    public BaseResult voting(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "投票ID") @RequestParam Integer voteId,
            @ApiParam(value = "投票标题") @RequestParam Boolean choice) {
        return contractInvoke.voting(token, invokeAddress, voteId, choice);
    }

    /**
     * 参与投票
     *
     * @param voteId 投票ID
     */
    @ApiOperation(value = "结束投票", notes = "投票系统")
    @RequestMapping(value = "/vote/endVoting", method = RequestMethod.POST)
    public BaseResult endVoting(
            @ApiParam(value = "accessToken") @RequestParam String token,
            @ApiParam(value = "调用者账户地址") @RequestParam String invokeAddress,
            @ApiParam(value = "投票ID") @RequestParam int voteId) {
        return contractInvoke.endVoting(token, invokeAddress, voteId);
    }

}
