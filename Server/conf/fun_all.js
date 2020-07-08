const config = require('../../config.json');
const axios = require('axios');
const db = require('../conf/config_database.js')
const jwt = require('jsonwebtoken');

exports.console_all = async (pr_content) => {
    console.log(pr_content)

    await axios.post(config.web_hook, {
            username: 'Server',
            content: pr_content
        }
    )
}

exports.token_make = function(send_email){
    return new Promise((resolve => {
        var sql = `SELECT * FROM User_Information WHERE user_email = '${send_email}'`
        var token = ''
        db.query(sql, function(err, rows){
            if(err) {
                throw err;
            }
            const payload = {
                idx : rows[0].idx,
                user_email : rows[0].user_email,
                nickname : rows[0].nickname,
                grade : rows[0].grade
            }
            token = jwt.sign(payload, config.secret_key); //첫번째 인자 : playlaod 2번째 인자 비밀키 값
            resolve(token)
        })
    }))
}
