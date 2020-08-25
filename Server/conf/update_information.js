const fun_all = require('./fun_all.js')
const db = require('./config_database') 
const config = require('../../config.json');

exports.update_nickname = async (req,res) =>{
    fun_all.console_all(req.body.change_nickname, req.body.email)
    fun_all.console_all("update_nickname 접속");
    var sql = `Update User_Information SET nickname = "${req.body.change_nickname}" WHERE user_email = "${req.body.email}"`;
    await db.query(sql, function(err, rows){
        if(!err) {
            console.log("update 성공");
            res.end(config.success)
        }else{
            console.log(err);
            res.end(config.failed)
        }   
    })
}