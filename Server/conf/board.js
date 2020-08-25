const fun_all = require('./fun_all.js')
const db = require('./config_database') 
const moment = require('moment');
const config = require('../../config.json');

exports.write_Bulletin = async (req, res) =>{
    fun_all.console_all("write_Bulletin 접속");
    // console.log(req.body.email, req.body.content, moment().format('YYYY-MM-DD HH:mm:ss'));
    var sql = "insert into Bulletin_Information(user_email, content, date, type, title) VALUES(?, ?, ?, ?, ?)";
    await db.query(sql, [req.body.email, req.body.content, moment().format('YYYY-MM-DD HH:mm:ss'), req.body.type, req.body.title], function(err, rows){
        if(!err) {
            console.log("입력 성공");
            res.end(config.success)
        }else{
            console.log(err);
            res.end(config.failed)
        }   
    })
}

exports.board = (req,res) =>{
    fun_all.console_all("board 접속");
    var aJsonArray = new Array();
    var aJson = new Object();
    for(var i = 0;  i < 10; i++){
        var aJson = new Object();
        aJson.likeCount = `${i}6`;
        aJson.section = "자유";
        aJson.content = "Lorem Ipsum, g iving information on its origins, as well as a random Lipsum generator.";
        aJson.writer = "양현승";
        aJson.viewer = 32;
        aJson.previous = `${i}초 전`;
        // console.log(aJson)
        aJsonArray.push(aJson);
    }
    // console.log(JSON.stringify(aJsonArray))
    res.end(JSON.stringify(aJsonArray))
}