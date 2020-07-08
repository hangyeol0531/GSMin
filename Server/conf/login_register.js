const db = require('./config_database')
const config = require('../../config.json');
const crypto = require('crypto'); 
const shortId = require('shortId')
const nodemailer = require('nodemailer');
var fun_all = require('./fun_all.js')

exports.logincheck = async (req,res) =>{
    fun_all.console_all(`login_check 접속 ${req.body.email}`)
    email = req.body.email;
    crytopassword = crypto.createHash('sha512').update(req.body.pw).digest('base64')
    var sql = `SELECT * FROM User_Information WHERE user_email = '${email}'`
    await db.query(sql, function(err, rows){
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){
            res.end('null');
        }else{
            if(rows[0].password == crytopassword){
                fun_all.token_make(email).then((token) =>{
                    var obj = new Object();
                    obj.token = token;
                    obj.email = rows[0].user_email;
                    obj.name = rows[0].nickname;
                    obj.grade = rows[0].grade;
                    res.json(obj)
                })
            }else{
                res.end(config.failed)
            }
        }
    })
}

exports.emailCheck = async (req,res) =>{
    await fun_all.console_all(`${req.body.email} : emailCheck 접속`);
    console.log("이메일 : " + req.body.email);
    const certification_code_str = shortId.generate();
    fun_all.console_all(certification_code_str)

    try{
        const smtpTransport = nodemailer.createTransport({
            service: "Gmail",
            auth: {
                user: config.send_email,
                pass: config.password
            },
            tls: {
                rejectUnauthorized: false
            }
          });
    
        
        const mailOptions = {
            from: config.send_email,
            to: req.body.email,
            subject: "광주소프트웨어마이스터고등학교 GSMin 가입 인증 코드입니다.",
            html: `
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
                <style>
                    body{
                        text-align: center;
                        background-color: bisque;
                        font-family:"돋움";
                        font-size: 1em;
                    }
                </style>
            </head>
            <body>
                <h2>GSMin 가입 철차 확인 이메일입니다.</h2>
                <hr width = "100%"><br><br>
                 본 이메일 인증은 GSMin 회원가입을 위한 필수 사항입니다. <br><br>
                 본인이 맞으시다면 GSMIN 확인창에  ${certification_code_str}  를 입력해주세요
            </body>
            </html>
            `
        };
        // 이메일 전송하려면 이쪽 주석을 풀어야함
        
        //  await smtpTransport.sendMail(mailOptions, (error, responses) =>{
        //     if(error){
        //         console.log(error)
        //     }else{
        //         console.log("이메일 전송 완료")
        //     }
        //     smtpTransport.close();
        // });

    }catch(e){
        console.log(e);
    }
    res.end(certification_code_str);
}

exports.insert_user_information = async (req, res)=>{
    const email = req.body.email;
    const crytopassword = crypto.createHash('sha512').update(req.body.pw).digest('base64');
    const nickname = req.body.nickname;
    const grade = Number(String(new Date().getFullYear()).slice(2,4))- Number(email.slice(1,3))+1
    if(grade >= 4){
        grade = 4;
    }
    var sql = "insert into User_Information(user_email, password, nickname, grade) VALUES(?, ?, ?, ?)";
    db.query(sql, [email, crytopassword, nickname, grade], function(err, rows){
        if(!err) {
            console.log("입력 성공");
            res.end(config.success)
        }else{
            console.log(err);
            res.end(config.failed)
        }   
    })
}


