var express = require('express'); //express 모듈 로드
var bodyParser = require('body-parser');
var app = express(); // express app 생성


const Discord = require('discord.js');
const nodemailer = require('nodemailer');
const client = new Discord.Client();
const config = require('../config.json');
var port = 3000;

const axios = require('axios');
const mysql = require('mysql');
const cors = require('cors');

const School = require('node-school-kr');
const school = new School()
school.init(School.Type.HIGH, School.Region.GWANGJU, 'F100000120');

app.use(cors()); 
app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json())

var db = mysql.createConnection({
    host: "localhost",
    database: config.sqlpassword,
    user: "root",
    password: config.dbname
  })

const console_all = async (pr_content) => {
    console.log(pr_content)

    await axios.post(config.web_hook, {
            username: 'Server',
            content: pr_content
        }
    )
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
            text: `GSMin가입에 대한 인증 절차입니다.`
        };
        
        // await smtpTransport.sendMail(mailOptions, (error, responses) =>{
        //     if(error){
        //         res.json({msg:'err'});
        //     }else{
        //         res.json({msg:'success'});
        //     }
        //     smtpTransport.close();
        // });
    }catch(e){
        console.log(e);
    }
    res.send("connect");
});

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

