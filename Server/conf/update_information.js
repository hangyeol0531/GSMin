const fun_all = require('./fun_all.js')
const db = require('./config_database') 
const config = require('../../config.json');

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
                res.end(token)
            })
        }else{
            console.log(err);
            res.end(config.failed)
        }   
    })
}