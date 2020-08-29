const fun_all = require('./fun_all.js')
const db = require('./config_database') 
const config = require('../../config.json');

exports.get_user_data = async(req, res) =>{
    fun_all.console_all('get_user_data 입장')
    console.log(req.body.email)
    var sql = `SELECT * FROM User_Information WHERE user_email = '${req.body.email}'`
    await db.query(sql, function(err, rows){
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){
            res.end('null');
        }else{
            var obj = new Object();
            obj.idx = rows[0].idx;
            obj.name = rows[0].nickname;
            obj.grade = rows[0].grade;
            res.json(obj)                
        }
        res.end(config.failed)
    })
}

exports.update_nickname = async (req,res) =>{
    fun_all.console_all(req.body.change_nickname, req.body.email);
    if(req.body == {}) res.end('status : 400')
    fun_all.console_all("update_nickname 접속");
    var sql = `Update User_Information SET nickname = "${req.body.change_nickname}" WHERE user_email = "${req.body.email}"`;
    await db.query(sql, function(err, rows){
        if(!err) {
            console.log("update 성공");
            fun_all.token_make(req.body.email).then((token) =>{
                // var obj = new Object();
                // obj.token = token;
                // obj.email = rows[0].user_email;
                // obj.name = rows[0].nickname;
                // obj.grade = rows[0].grade;
                // res.json(obj)
                var obj = new Object();
                obj.status = 200;
                obj.token = token
                res.json(obj)
            })
        }else{
            console.log(err);
            res.end(config.failed)
        }   
    })
}