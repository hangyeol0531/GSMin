var express = require('express'); //express 모듈 로드
var bodyParser = require('body-parser');
var app = express(); // express app 생성
const Discord = require('discord.js');
const nodemailer = require('nodemailer');
const client = new Discord.Client();
const config = require('../config.json');
const axios = require('axios');
var port = 3000;

app.use(bodyParser.urlencoded({extended:true}))
app.use(bodyParser.json())

const console_all = async (pr_content) => {
    console.log(pr_content)

    await axios.post(config.web_hook, {
            username: 'Server',
            content: pr_content
        }
    )
}


app.get('/', (req,res) =>{

});

app.post('/emailCheck', async (req,res)=>{
    await console_all(`${req.body.email} : emailCheck 접속`);
    console.log("이메일 : " + req.body.email);

    const smtpTransport = nodemailer.createTransport({
        service: "Gmail",
        auth: {
            user: "a84225523@gmail.com",
            pass: config.password
        },
        tls: {
            rejectUnauthorized: false
        }
      });

    
    const mailOptions = {
        from: "a84225523@gmail.com",
        to: req.body.email,
        subject: "인증해주세요",
        text: "GSMin가입에 대한 인증 절차입니다."
    };
    
    await smtpTransport.sendMail(mailOptions, (error, responses) =>{
        if(error){
            res.json({msg:'err'});
        }else{
            res.json({msg:'success'});
        }
        smtpTransport.close();
    });
      
    res.send("connect");
});

app.listen(port, function(){
    console.log(`${port}포트로 서버 실행`);
});


//----------------------------------------
client.on('ready', () => {
    console.log(`Logged in as ${client.user.tag}!`);
});



client.login(config.token)

