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
    let flag = 0;
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
                flag = 1;
            }
            if(flag){
                console.log(aJsonArray.length)
                //TODO ----1
                if(aJsonArray.length >= 1){
                let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[0].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[0].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[0].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[0].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 1) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----2
                if(aJsonArray.length >= 2){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[1].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[1].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[1].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[1].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 2) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----3
                if(aJsonArray.length >= 3){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[2].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[2].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[2].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[2].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 3) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----4
                if(aJsonArray.length >= 4){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[3].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[3].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[3].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[3].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 4) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }

                //TODO ----5
                if(aJsonArray.length >= 5){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[4].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[4].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[4].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[4].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 5) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }

                //TODO ----6
                if(aJsonArray.length >= 6){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[5].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[5].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[5].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[5].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 6) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }

                //TODO ----7
                if(aJsonArray.length >= 7){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[6].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[6].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[6].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[6].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 7) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----8
                if(aJsonArray.length >= 8){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[7].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[7].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[7].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[7].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 8) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }

                //TODO ----9
                if(aJsonArray.length >= 9){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[8].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[8].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[8].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[8].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 9) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----10
                if(aJsonArray.length >= 10){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[9].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[9].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[9].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[9].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 10) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }



            }       
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
    var aJson = new Object();
    await db.query(sql, async function(err, rows){
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){ 
            res.end('null');
        }else{
            let view_count = rows[0].view_count;
            if(view_count == []) view_count = 1;
            else view_count += 1;
            // console.log(view_count)
            aJson.idx = rows[0].idx
            aJson.user_email = rows[0].user_email;
            aJson.title = rows[0].title;
            aJson.content = rows[0].content;
            aJson.date = rows[0].date;
            aJson.view_count = view_count;
            var sql2 = `Update Bulletin_Information SET view_count = "${view_count}" WHERE idx = "${rows[0].idx}"`;
            await db.query(sql2, (err, rows) =>{
            })
            var sql3 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = ${req.body.idx};`
            await db.query(sql3, async (err, rows) =>{
                console.log(rows[0])
                aJson.Comment_count = rows[0]['COUNT(*)'];
                var sql4 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = ${req.body.idx};`
                await db.query(sql4, (err, rows) => {
                    console.log(rows[0])
                    aJson.good_count = rows[0]['COUNT(*)'];
                    res.end(JSON.stringify(aJson))
                })
            })
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
    if(req.body.email !== undefined){
        var aJson = new Object();
        var sql3 = `SELECT COUNT(*) FROM Comment_information WHERE user_email = "${req.body.email}";`
        await db.query(sql3, async (err, rows) =>{
            aJson.Comment_count = rows[0]['COUNT(*)'];
            var sql4 = `SELECT COUNT(*) FROM Bulletin_Information WHERE user_email = "${req.body.email}";`
            await db.query(sql4, (err, rows) => {
                aJson.Bulletin_count = rows[0]['COUNT(*)'];
                res.end(JSON.stringify(aJson))
            })
        })
    }else{
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
                    res.end('추가')
                }
            })
        }else if(check_Code == 1){
            sql = `DELETE FROM good_board WHERE Bulletin_idx = ${req.body.Bulletin_idx} AND user_email = "${req.body.email}";`;
            await db.query(sql, (err, rows) =>{
                if(err){
                    console.log(err);
                    res.end(config.failed);
                }else{
                    res.end('삭제')
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

//TODO -- mydata

exports.get_my_list = async(req, res) =>{
    console.log('get_my_list 입력')
    let flag = 0;
    console.log(req.body.email, req.body.b_c, req.body.page_num)
    req.body.page_num = Number(req.body.page_num);
    if(req.body.b_c == 'b' ){
        var sql = `SELECT * FROM Bulletin_Information WHERE user_email = "${req.body.email}" LIMIT 10 OFFSET ${10*(req.body.page_num - 1)}`
    }else if(req.body.b_c == 'c'){
        var sql = `SELECT * FROM Comment_information WHERE user_email = "${req.body.email}" LIMIT 10 OFFSET ${10*(req.body.page_num - 1)}`
    }
    await db.query(sql, (err, rows) => {
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){ 
            res.end('null');
        }else{
            var aJsonArray = new Array();

            for(i = 0;  i < rows.length; i++){
                length = rows.length;
                var aJson = new Object();
                if(req.body.b_c == 'b'){
                    aJson.idx = rows[i].idx;
                    aJson.email = rows[i].user_email;
                    aJson.title = rows[i].title;
                    aJson.content = rows[i].content;
                    aJson.date = rows[i].date;
                    aJson.type = rows[i].type;
                    aJson.view_count = rows[i].view_count;
                    aJsonArray.push(aJson);
                    if(i == rows.length - 1) {
                        flag = 1;
                    }
                }else if(req.body.b_c == 'c'){
                    aJson.Bulletin_idx = rows[i].Bulletin_idx;
                    aJson.idx = rows[i].idx;
                    aJson.user_email = rows[i].user_email;
                    aJson.date = rows[i].date;
                    aJson.comment = rows[i].comment;
                    aJsonArray.push(aJson);
                }
            }
            if(flag){
                //TODO ----1
                if(aJsonArray.length >= 1){
                    console.log('1입장')
                let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[0].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[0].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[0].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[0].comment_count = rows[0]['COUNT(*)']
                            console.log(aJsonArray[0])
                            if(aJsonArray.length == 1) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----2
                if(aJsonArray.length >= 2){
                    console.log('2입장')
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[1].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[1].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[1].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[1].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 2) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----3
                if(aJsonArray.length >= 3){
                    console.log('3입장')
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[2].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[2].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[2].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[2].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 3) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----4
                if(aJsonArray.length >= 4){
                    console.log('4입장')
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[3].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[3].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[3].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[3].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 4) {
                                console.log(aJsonArray)
                                res.status(200).send(JSON.stringify(aJsonArray));
                            }
                        })
                    })
                }
                //TODO ----5
                if(aJsonArray.length >= 5){
                    console.log('5입장')
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[4].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[4].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[4].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[4].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 5) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----6
                if(aJsonArray.length >= 6){
                    console.log('6입장')
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[5].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[5].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[5].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[5].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 6) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }

                //TODO ----7
                if(aJsonArray.length >= 7){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[6].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[6].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[6].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[6].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 7) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----8
                if(aJsonArray.length >= 8){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[7].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[7].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[7].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[7].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 8) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----9
                if(aJsonArray.length >= 9){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[8].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[8].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[8].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[8].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 9) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
                //TODO ----10
                if(aJsonArray.length >= 10){
                    let sql1 = `SELECT COUNT(*) FROM good_board WHERE Bulletin_idx = "${aJsonArray[9].idx}";`
                    db.query(sql1,(err, rows) =>{
                        aJsonArray[9].good_count = rows[0]['COUNT(*)']
                        var sql2 = `SELECT COUNT(*) FROM Comment_information WHERE Bulletin_idx = "${aJsonArray[9].idx}";`
                        db.query(sql2,(err, rows) =>{
                            aJsonArray[9].comment_count = rows[0]['COUNT(*)']
                            if(aJsonArray.length == 10) res.status(200).send(JSON.stringify(aJsonArray));
                        })
                    })
                }
            }

            // console.log(JSON.stringify(aJsonArray))
            // res.status(200).send(JSON.stringify(aJsonArray))
        }
    })
}