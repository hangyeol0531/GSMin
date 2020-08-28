const fun_all = require('./fun_all.js')
const db = require('./config_database') 
const moment = require('moment');
const config = require('../../config.json');


// exports.board = (req,res) =>{
//     fun_all.console_all("board 접속");
//     var aJsonArray = new Array();
//     var aJson = new Object();
//     for(var i = 0;  i < 10; i++){
//         var aJson = new Object();
//         aJson.likeCount = `${i}6`;
//         aJson.section = "자유";
//         aJson.content = "Lorem Ipsum, g iving information on its origins, as well as a random Lipsum generator.";
//         aJson.writer = "양현승";
//         aJson.viewer = 32;
//         aJson.previous = `${i}초 전`;
//         // console.log(aJson)
//         aJsonArray.push(aJson);
//     }
//     // console.log(JSON.stringify(aJsonArray))
//     res.end(JSON.stringify(aJsonArray))
// }

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

exports.write_comment = async(req, res) =>{
    fun_all.console_all("wrtie comment 접속");
    var sql = "insert into Comment_information(Bulletin_idx, user_email, comment, date) VALUES(?, ?, ?, ?)";
    await db.query(sql, [req.body.idx, req.body.email, req.body.comment, moment().format('YYYY-MM-DD HH:mm:ss')], function(err, rows){
        if(!err) {
            console.log("입력 성공");
            res.end(config.success)
        }else{
            console.log(err);
            res.end("config.failed")
        }   
    })
}   

exports.get_board_information = async (req,res) =>{
    fun_all.console_all("get_broad_information 접속");
    console.log(req.body.type, req.body.page_num)
    var sql = `SELECT * FROM (SELECT * FROM Bulletin_Information WHERE type = "${req.body.type}" ORDER BY date DESC) as A LIMIT 10 OFFSET ${10*(req.body.page_num - 1)} `
    await db.query(sql, function(err, rows){
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){ 
            res.end('null');
        }else{
            var aJsonArray = new Array();
            var aJson = new Object();
            for(var i = 0;  i < rows.length; i++){
                var aJson = new Object();
                aJson.idx = rows[i].idx;
                aJson.title = rows[i].title;
                aJson.date = rows[i].date;
                aJsonArray.push(aJson);
            }
            console.log(JSON.stringify(aJsonArray))
            res.end(JSON.stringify(aJsonArray))
        }
    })
}

exports.get_comment_information = async (req,res) =>{
    fun_all.console_all("get_comment_information 접속");
    console.log(req.body.idx)
    var sql = `SELECT * FROM Comment_information WHERE Bulletin_idx = "${req.body.idx}" ORDER BY date DESC `
    await db.query(sql, function(err, rows){
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){ 
            res.end('null');
        }else{
            var aJsonArray = new Array();
            var aJson = new Object();
            for(var i = 0;  i < rows.length; i++){
                var aJson = new Object();
                aJson.idx = rows[i].idx;
                aJson.title = rows[i].title;
                aJson.date = rows[i].date;
                aJsonArray.push(aJson);
            }
            console.log(JSON.stringify(aJsonArray))
            res.end(JSON.stringify(aJsonArray))
        }
    })
}

exports.trash_Data = async (req,res) =>{
    var max = 100;
    var min = 1;
    for(var i = 0; i <= 10; i++){
        var random_num = Math.floor(Math.random() * (max - min)) + min;
        var sql = `insert into Bulletin_Information(user_email, content, date, type, title) VALUES("${random_num}@gsm.hs.kr", "${random_num}@gsm.hs.kr님의 게시글", "${moment().format('YYYY-MM-DD HH:mm:ss')}", "${req.body.type}", "${random_num}의 제목")`;
        await db.query(sql, function(err, rows){
            if(!err) {
                console.log("입력 성공");
                res.end(config.success)
            }else{
                console.log(err);
                res.end(config.failed)
            }   
        })
    }
}

