var express = require('express'); //express 모듈 로드
var bodyParser = require('body-parser');
var app = express(); // express app 생성

const Discord = require('discord.js');
const nodemailer = require('nodemailer');
const client = new Discord.Client();
const config = require('../config.json');
var port = 80;

const axios = require('axios');
const mysql = require('mysql');
const cors = require('cors');
const shortId = require('shortId')
const crypto = require('crypto'); 
const logger = require('morgan');
const jwt = require('jsonwebtoken');

const School = require('node-school-kr');
const school = new School()
var ip = require('ip');

school.init(School.Type.HIGH, School.Region.GWANGJU, 'F100000120');

app.use(logger("short"));
app.use(cors()); 
app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json())

app.use((req, res, next) => {
    console_all ( `${ip.address()} 님이 서버를 접속하셨습니다.` );
    next()
})

var db = mysql.createConnection({
    host: "localhost",
    database: config.dbname,
    user: "root",
    password: config.sqlpassword
  })

const console_all = async (pr_content) => {
    console.log(pr_content)

    await axios.post(config.web_hook, {
            username: 'Server',
            content: pr_content
        }
    )
}

app.post('/token', (req, res) =>{
    const payload = {
        idx : req.body.idx,
        email : req.body.email,
        nickname : req.body.nickname
    }
    var token = jwt.sign(payload, config.secret_key); //첫번째 인자 : playlaod 2번째 인자 비밀키 값
    console.log(token);
    try{
        decoded_token = jwt.verify(token, config.secret_key)
        res.end('token is ggul dda ri')
    }catch{
        res.end('token is invalid')
    }
    
})

app.post('/gsmschoolfood', async (req, res) =>{
    console_all("gsmschoolfood 접속")
    await console_all(`${req.body.dateyear} : ${req.body.datemonth} : ${req.body.dateday}`);
    await console_all(req.body.eatTime);

    let meal = await school.getMeal()
    let meal_info;
    if(meal.today == "") meal_info = "오늘 급식 정보가 없습니다.";
    else meal_info = meal.today
    console_all(meal_info);

    let calendar = await school.getCalendar(req.body.dateyear, req.body.datemonth, req.body.dateday)
    let cal_info;
    
    if(calendar[String(todayday)] = "") cal_info ="오늘 일정 정보가 없습니다." 
    else cal_info = calendar[String(todayday)]  
    console_all(cal_info);
    const send_food_obj = `{'meal' : ${meal_info}, 'cal' : ${cal_info}}, 'kind'x     : "gsmschoolfood"`
    var send_obj = JSON.parse(send_food_obj);
    res.end(send_obj)
})

app.post('/emailCheck', async (req,res)=>{
    await console_all(`${req.body.email} : emailCheck 접속`);
    console.log("이메일 : " + req.body.email);
    const certification_code_str = shortId.generate();
    console_all(certification_code_str)

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
});

app.post('/insert_user_information', (req, res) =>{
    const email = req.body.email;
    const crytopassword = crypto.createHash('sha512').update(req.body.pw).digest('base64');
    const nickname = req.body.nickname;
    console.log(email)
    console.log(crytopassword)
    console.log(nickname)
    var sql = "insert into User_Information(user_email, password, nickname) VALUES(?, ?, ?)";
    db.query(sql, [email, crytopassword, nickname], function(err, rows){
        if(!err) {
            console.log("입력 성공");
            res.end(config.success)
        }else{
            console.log(err);
            res.end(config.failed)
        }   
    })

})

app.post('/req_hire_list', (req, res) =>{
    
})

app.post('/login_check', (req, res) =>{
    email = req.body.email;
    crytopassword = crypto.createHash('sha512').update(req.body.pw).digest('base64');
    console.log(email, crytopassword)
    var sql = `SELECT * FROM User_Information WHERE user_email = '${email}'`
    db.query(sql, function(err, rows){
        console.log(rows)
        if(err) {
            throw err;
        }else if(JSON.stringify(rows) == '[]'){
            res.end('null');
        }else{
            if(rows[0].password == crytopassword){
                res.end(config.success)
            }else{
                res.end(cofng.failed)
            }
        }
    })
})

app.post('/board', async (req,res) =>{
    console_all("board 접속");
    var aJsonArray = new Array();
    var aJson = new Object();
    for(var i = 0;  i < 10; i++){
        var aJson = new Object();
        aJson.likeCount = `${i}6`;
        aJson.section = "자유";
        aJson.content = "Lorem Ipsum, giving information on its origins, as well as a random Lipsum generator.";
        aJson.writer = "양현승";
        aJson.viewer = 32;
        aJson.previous = `${i}초 전`;
        console.log(aJson)
        aJsonArray.push(aJson);
    }
    console.log(JSON.stringify(aJsonArray))
    res.end(JSON.stringify(aJsonArray))
})

app.listen(port, function(){
    console.log(`${port}포트로 서버 실행`);
});

//----------------------------------------
client.on('ready', () => {
    console.log(`Logged in as ${client.user.tag}!`);
});



client.login(config.token)

