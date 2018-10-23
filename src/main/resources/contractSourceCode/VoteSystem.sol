pragma solidity ^0.4.10;

library SafeMath{
    function mul(uint8 a , uint8 b ) internal returns( uint8 ){
        if(a == 0 ){
            return 0;
        }
        uint8 c = a * b ;
        assert(c /a ==  b );
        return c;
    }
    function div(uint256 a , uint256 b ) internal returns( uint256 ){
        uint256 c = a / b ;
        return c;
    }
    function sub(uint256 a , uint256 b ) internal returns( uint256 ){
        assert(b<=a);
        return a - b;
    }
    function add(uint256 a , uint256 b ) internal returns( uint256 ){
        uint256 c = a + b;
        assert(c >= a);
        return c;
    }
}

contract VoteSystem {
    /*
    * @title: 智能合约开发-作业二-编程题-投票系统
    * @author: LauItachi
    * @dev:
    * ① 可以发布投票 postVote
    * ② 可参与投票（所有人） voting 注：实名投票，保存参与投票者的地址，可用于后期追溯或进行问题调查
    * ③ 结束投票（发布者） endVoting
    * ④ 验证投票结果 agreeThanDisagree 当系统内超过3/2的人参与投票且1/2以上的投了赞成票，则投票确认，否则投票否决。
    * ⑤ 保持函数单一出口
    * @notice: 解题提示：可以保存当前的参与系统的用户账户；保存投票内容等；投票内容涉及发布者等信息。
    */

    using SafeMath for uint8;

    struct Vote{
        uint voteId;
        address poster; // 投票发起者
        bytes32 content; // 投票内容（如：关于xxxx的投票）
        address[] participants; // 投票参与者数组
        bool[] choices;  // 选票数组
        bool state; // 投票的状态，true-开启，false-关闭
        // 严格来讲还需要 开始时间和结束时间等控制条件 这边简单实现只用state来标记状态
    }
    Vote[] votes; // 用于保存多组投票
    
    uint sum; // 系统总人数
    uint autoId; // 用于自增VoteId
    
    // 定义状态码
    uint CODE_SUCCESS = 3300; // 操作正常
    uint CODE_INSUFFICIENT_PERMISSION = 3401; // 权限异常（非发起者）
    uint CODE_VOTE_PASSED = 3402; // 投票通过
    uint CODE_VOTE_FAILED = 3403; // 投票未通过
    uint CODE_VOTE_CLOSED = 3404; // 投票已关闭
    uint CODE_UNKNOW_ERROR= 3405; // 未知错误
     
    // 合约构造函数
    function VoteSystem(uint s) public { // 开启系统时给定参与人数
        sum = s;
    }
    // 发起一组投票
    function postVote(bytes32 content) public returns(uint){
        uint resultCode = CODE_UNKNOW_ERROR; // 默认未知错误
        address[] memory participants; // 投票参与者数组
        bool[] memory choices;
        Vote memory vote = Vote({voteId: autoId++, poster: msg.sender, content: content, participants: participants, choices: choices, state: true});
        votes.push(vote);
        resultCode = CODE_SUCCESS; // 修改状态为操作成功
        return resultCode;
    }
    // 参与一组投票
    function voting(uint voteId, bool choice) public returns(uint){
        uint resultCode = CODE_UNKNOW_ERROR; // 默认未知错误
        Vote storage vote = votes[voteId]; //优化，提前取出vote，避免每次读取votes数组，增加后续代码可扩展性
        if( vote.state ){
            vote.choices.push(choice);
            vote.participants.push(msg.sender);
            resultCode = CODE_SUCCESS; // 修改状态为操作(投票)成功
        }else{
            resultCode = CODE_VOTE_CLOSED; // 投票已关闭
        }
        return resultCode;
    }
    // 停止一组投票
    function endVoting(uint voteId) public returns(uint){
        uint resultCode = CODE_UNKNOW_ERROR; // 默认未知错误
        Vote storage vote = votes[voteId]; //优化，提前取出vote，避免在判断中每次读取votes数组
        if( vote.poster != msg.sender ){ // 若不是该组投票发起者
            resultCode = CODE_INSUFFICIENT_PERMISSION; // 权限异常
        }else if( !vote.state ){ // 判断状态
            resultCode = CODE_VOTE_CLOSED; // 投票已关闭
        }else if( vote.participants.length > sum*2/3 && agreeThanDisagree(voteId) ){
            // 当系统内超过3/2的人参与投票且1/2以上的投了赞成票，则投票确认，否则投票否决。
            resultCode = CODE_VOTE_PASSED;
        }else{
            resultCode = CODE_VOTE_FAILED;
        }
        
        return resultCode;
    }
    
    function agreeThanDisagree(uint voteId) internal returns(bool){
        bool result = false;
        uint count = 0;
        Vote memory vote = votes[voteId]; // 优化，提前取出放入内存
        address[] memory participants = vote.participants; // 优化，提前取出放入内存
        uint num = participants.length; // 优化，提前取出投票人数，避免循环重复获取
        for( uint i = 0 ; i < num ; i++ ){
            if( vote.choices[i] == true ){
                count++;
                // 若需统计赞同者名单，也可在此处通过vote.participants[i]对应取出
            }
        }
        if( count > num.sub(count) ){ // 赞同者数量大于反对者数量
            result = true;
        }
        return result;
    }
     
     
}