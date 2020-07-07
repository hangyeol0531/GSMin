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
const { stringify } = require('querystring');
const { decode } = require('punycode');

school.init(School.Type.HIGH, School.Region.GWANGJU, 'F100000120');

app.use(logger("short"));
app.use(cors()); 
app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json())

app.use((req, res, next) => {
    var ip = req.headers['x-forwarded-for'] ||
     req.connection.remoteAddress ||
     req.socket.remoteAddress ||
     req.connection.socket.remoteAddress;
    let name;

    if(ip.includes('73.38')){
        name = '서지우 공주님'
    }else if(ip.includes('73.39')){
        name = '양현승 왕자님'
    }else if(ip.includes('68.184')){
        name = '햇갯님'
    }
    console_all ( `${name}이 서버를 접속하셨습니다.` );
    next();
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


// app.post('/token', (req, res) =>{
//     const payload = {
//         idx : req.body.idx,
//         email : req.body.email,
//         nickname : req.body.nickname,
//         grade : req.body.grade
//     }
//     var token = jwt.sign(payload, config.secret_key); //첫번째 인자 : playlaod 2번째 인자 비밀키 값
//     console.log(token);
//     try{
//         decoded_token = jwt.verify(token, config.secret_key)
//         console.log(decoded_token)
//         res.end('token is ggul dda ri')
//     }catch{
//         res.end('token is invalid')
//     }
// })

var token_make = function(send_email){
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
});

app.post('/insert_user_information', (req, res) =>{
    const email = req.body.email;
    const crytopassword = crypto.createHash('sha512').update(req.body.pw).digest('base64');
    const nickname = req.body.nickname;
    const grade = Number(String(new Date().getFullYear()).slice(2,4))- Number(email.slice(1,3))+1
    if(grade >= 4){
        grade = 4;
    }
    console.log(grade)
    console.log(email)
    console.log(crytopassword)
    console.log(nickname)
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

})

app.get('/gsm_hire_list', async (req, res) =>{
    const job_list = await axios.get('https://sheet.best/api/sheets/a954e6a3-0e4f-48a6-8a20-234ff289fffd');
    const spread_list = job_list[6]
    res.end(JSON.stringify(job_list.data.slice(6, -1)));
})

app.get('/gsm_employment_rate', async(req, res) =>{
    const employment_rate = await axios.get('https://sheet.best/api/sheets/a2e05329-b7f6-4824-8295-eaa9ba36e5bd');
    const employment_rate_data = employment_rate.data.slice(5, -1)
    let emp_num;
    console.log(employment_rate_data[1]['2'])
    for (var i = 0; i < 80; i++){
        if(!(employment_rate_data[i]['2'])){
            emp_num = i;
            break;
        }
    }
    console.log(typeof(emp_num))
    res.end(String(emp_num * 1.25));
})
app.post('/receive_token_inforation', (req, res) =>{
    try{
        // decoded_token = jwt.verify(req.body.user_token, config.secret_key)
        // res.end(decoded_token)
        jwt.verify(req.body.user_token, config.secret_key, (err,decoded) =>{
            if(err){
                console.log(err)
            }else{
                res.end(JSON.stringify(decoded))
            }
        })
    }catch(err){
        console.log(err)
        res.end('token is invalid')
    }
})

app.post('/login_check', async (req, res) =>{
    console_all(`login_check 접속 ${req.body.email}`)
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
                token_make(email).then((token) =>{
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
        // console.log(aJson)
        aJsonArray.push(aJson);
    }
    // console.log(JSON.stringify(aJsonArray))
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

