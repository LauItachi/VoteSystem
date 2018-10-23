package cn.hyperchain.application.contract.invoke;

import cn.hyperchain.application.common.constant.Code;
import cn.hyperchain.application.common.response.BaseResult;
import cn.hyperchain.application.common.response.BaseResultFactory;
import cn.hyperchain.application.common.utils.ContractUtils;
import cn.qsnark.sdk.exception.TxException;
import cn.qsnark.sdk.rpc.QsnarkAPI;
import cn.qsnark.sdk.rpc.function.FuncParamReal;
import cn.qsnark.sdk.rpc.function.FunctionDecode;
import cn.qsnark.sdk.rpc.returns.GetTxReciptReturn;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * @author lauItachi
 * @date 2018/10/19
 */
@Component
public class ContractInvoke {

    private static QsnarkAPI api = new QsnarkAPI();

    private final static String FLAG_OK = "ok";

    /**
     * 新建投票
     *
     * @return code
     */
    public BaseResult postVote(String token, String invokeAddress, String content) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[1];
        arrFunParamReal[0] = new FuncParamReal("bytes32", content);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "postVote",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("postVote", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("postVote", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

    /**
     * 对应于合约的voting方法
     *
     * @return code
     */
    public BaseResult voting(String token, String invokeAddress, Integer voteId, Boolean choice) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[2];
        arrFunParamReal[0] = new FuncParamReal("uint256", voteId);
        arrFunParamReal[1] = new FuncParamReal("bool", choice);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "voting",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("voting", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("voting", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }


    /**
     * 对应于合约的voting方法
     *
     * @return code
     */
    public BaseResult endVoting(String token, String invokeAddress, int voteId) {
        //构造参数
        FuncParamReal[] arrFunParamReal = new FuncParamReal[1];
        arrFunParamReal[0] = new FuncParamReal("uint256", voteId);
        GetTxReciptReturn getTxReciptReturn = null;
        try {
            getTxReciptReturn = api.invokesyncContract(
                    token,
                    false,
                    invokeAddress,
                    ContractUtils.getContractAddress(),
                    ContractUtils.getAbi(),
                    "endVoting",
                    arrFunParamReal
            );
        } catch (IOException | TxException | InterruptedException e) {
            e.printStackTrace();
        }

        BaseResult baseResult = null;

        if (FLAG_OK.equals(getTxReciptReturn.getStatus())) {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.SUCCESS,
                        FunctionDecode.resultDecode("endVoting", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            try {
                baseResult = BaseResultFactory.produceResult(
                        Code.INVOKE_FAIL,
                        FunctionDecode.resultDecode("endVoting", ContractUtils.getAbi(), getTxReciptReturn.getRet()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return baseResult;
    }

}
