const fun_all = require('./fun_all.js')
const db = require('./config_database') 
const moment = require('moment');
const config = require('../../config.json');
const { fromLong } = require('ip');

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
            res.end(config.failed)
        }   
    })
}   

exports.delete_board = async(req, res) =>{
    fun_all.console_all("delete_board");
    var sql = `delete from Bulletin_Information where idx = ${req.body.idx}`;
    await db.query(sql, function(err, rows){
        if(!err) {
            console.log("삭제 성공");
            res.end(config.success)
        }else{
            console.log(err);
            res.end(config.failed)
        }   
    })
}

exports.delete_comment = async(req, res) =>{
    fun_all.console_all("delete_comment");
    var sql = `delete from Comment_information where idx = ${req.body.idx}`;
    await db.query(sql, function(err, rows){
        if(!err) {
            console.log("삭제 성공");
            res.end(config.success)
        }else{
            console.log(err);
            res.end(config.failed)
        }   
    })
}

exports.get_board_information = async (req,res) =>{
    fun_all.console_all("get_board_information 접속");
    console.log(req.body.type, req.body.page_num)
    req.body.page_num = Number(req.body.page_num);
    if(req.body.type == '전체' ){
        var sql = `SELECT A.idx, A.user_email, A.title, A.content, A.date, A.type, A.view_count, U.grade, U.nickname FROM 
        (SELECT * FROM Bulletin_Information ORDER BY date DESC) 
            as A, User_Information AS U Where A.user_email = U.user_email LIMIT 10 OFFSET ${10*(req.body.page_num - 1)} `    
    }else{
        var sql = `SELECT A.idx, A.user_email, A.title, A.content, A.date, A.type, A.view_count, U.grade, U.nickname FROM 
        (SELECT * FROM Bulletin_Information WHERE type = "${req.body.type}" ORDER BY date DESC) 
            as A, User_Information AS U Where A.user_email = U.user_email LIMIT 10 OFFSET ${10*(req.body.page_num - 1)} `    
    }
    await db.query(sql, async function(err, rows){
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){ 
            res.end('null');
        }else{
            var aJsonArray = new Array();
            for(var i = 0;  i < rows.length; i++){
                var aJson = new Object();
                aJson.idx = rows[i].idx;
                aJson.email = rows[i].user_email;
                aJson.title = rows[i].title;
                aJson.content = rows[i].content;
                aJson.date = rows[i].date;
                aJson.type = rows[i].type;
                aJson.nickname = rows[i].nickname;
                aJson.grade = rows[i].grade
                aJson.view_count = rows[i].view_count;
                aJsonArray.push(aJson);
            }
            console.log(JSON.stringify(aJsonArray))
            res.status(200).send(JSON.stringify(aJsonArray))
        }
    })
}

exports.get_comment_information = async (req,res) =>{
    fun_all.console_all("get_comment_information 접속");
    // console.log(req.body.idx)
    if(req.body.idx == undefined) res.status('404').end('req.body.idx null')
    var sql = `SELECT * FROM Comment_information as A, User_Information AS U 
    WHERE Bulletin_idx = ${req.body.idx} AND A.user_email = U.user_email ORDER BY date DESC ;`
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
                aJson.Bulletin_idx = rows[i].Bulletin_idx;
                aJson.user_email = rows[i].user_email;
                aJson.comment = rows[i].comment;
                aJson.date = rows[i].date;
                aJson.nickname = rows[i].nickname;
                aJson.grade = rows[i].grade
                aJsonArray.push(aJson);
            }
            console.log(JSON.stringify(aJsonArray))
            res.end(JSON.stringify(aJsonArray))
        }
    })
}

exports.get_one_board = async (req,res) =>{
    fun_all.console_all("get_one_board 접속");
    console.log(req.body.idx)
    // let flag = 0;
    var sql = `SELECT * FROM Bulletin_Information WHERE idx = "${req.body.idx}"`
    await db.query(sql, function(err, rows){
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){ 
            res.end('null');
        }else{
            let view_count = rows[0].view_count;
            if(view_count == []) view_count = 1;
            else view_count += 1;
            console.log(view_count)
            var sql2 = `Update Bulletin_Information SET view_count = "${view_count}" WHERE idx = "${rows[0].idx}"`;
            db.query(sql2, (err, rows) =>{
            })
            var aJson = new Object();
                aJson.idx = rows[0].idx;
                aJson.user_email = rows[0].user_email;
                aJson.title = rows[0].title;
                aJson.content = rows[0].content;
                aJson.date = rows[0].date;
                aJson.view_count = view_count;
                res.end(JSON.stringify(aJson))
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

exports.check_writer = async(req, res) =>{
    console.log(req.body.idx, req.body.check_Code) // 1 = board else comment
    if(req.body.check_Code == 1) var sql = `SELECT * FROM Bulletin_Information WHERE idx = "${req.body.idx}"`;
    else var sql = `SELECT * FROM Comment_information WHERE idx = "${req.body.idx}"`;
    
    await db.query(sql, function(err, rows){
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){ 
            res.end('null');
        }else{
            res.end(rows[0].user_email)
        }
     })
}

exports.board_num = async(req, res) =>{
    if(req.body.type === undefined) var sql = `SELECT Count(*) FROM Bulletin_Information;`;
    else var sql = `SELECT Count(*) FROM Bulletin_Information WHERE type = "${req.body.type}";`;
    await db.query(sql, function(err, rows){
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){ 
            res.end('null');
        }else{
            res.end(JSON.stringify(rows[0]['Count(*)']))
        }
     })
}

//TODO -- ㄸㅃ

exports.isgoodCheck = async (req ,res) => { 

    console.log(req.body.Bulletin_idx, req.body.email)
    let sql = `SELECT * FROM good_board WHERE Bulletin_idx = "${req.body.Bulletin_idx}" AND user_email = "${req.body.email}";`
    await db.query(sql, async (err, rows) =>{
        console.log(rows);
        if(rows.length == 0) res.end('0')
        else res.end('1')
    })
}

exports.isgood = async (req ,res) => {
    let check_Code = 0;
    let sql = `SELECT * FROM good_board WHERE Bulletin_idx = "${req.body.Bulletin_idx}" AND user_email = "${req.body.email}";`
    await db.query(sql, async (err, rows) =>{
        console.log(rows);
        if(rows.length == 0) check_Code = 0;
        else check_Code = 1;
        
        if(check_Code == 0){
            console.log(`isgood 접속 ${req.body.Bulletin_idx, req.body.email}`);
            sql = "insert into good_board(user_email, Bulletin_idx) VALUES(?, ?)";
            await db.query(sql, [req.body.email, req.body.Bulletin_idx],(err, rows) =>{
                if(err){
                    console.log(err);
                    res.end(config.failed);
                }else{
                    res.end(config.success)
                }
            })
        }else if(check_Code == 1){
            sql = `DELETE FROM good_board where Bulletin_idx = ${req.body.Bulletin_idx} AND user_email = ${ req.body.email};`;
            await db.query(sql, (err, rows) =>{
                if(err){
                    console.log(err);
                    res.end(config.failed);
                }else{
                    res.end(config.success)
                }
            })
        }
    })}


exports.isgood_num = async (req ,res) => { 
    console.log(req.body.Bulletin_idx)
    var sql = `SELECT Count(*) from GSMinDB.good_board where Bulletin_idx = ${req.body.Bulletin_idx};`
    await db.query(sql,(err, rows) =>{
        if(err){
            console.log(err);
            res.end(config.failed);
        }else{
            console.log(rows[0]['Count(*)'])
            res.status(201).send(rows[0]['Count(*)'].toString())
        }
    })
}


