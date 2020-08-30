var express = require('express'); //express 모듈 로드
var bodyParser = require('body-parser');
var app = express(); // express app 생성

const Discord = require('discord.js');
const nodemailer = require('nodemailer');
const client = new Discord.Client();
const config = require('../../config.json');
const db = require('../conf/config_database.js')
var port = 80;

const axios = require('axios');

const cors = require('cors');
const shortId = require('shortid')
const crypto = require('crypto'); 
const logger = require('morgan');
const jwt = require('jsonwebtoken');
var fs = require('fs');    

const School = require('node-school-kr');
const school = new School()
var ip = require('ip');
const { stringify } = require('querystring');
const { decode } = require('punycode');
const { send } = require('process');

var fun_all = require('../conf/fun_all')
var board_func = require('../conf/board.js')
var login_register_func = require('../conf/login_register.js')
var token_data_func = require('../conf/token_data.js')
var get_school_func = require('../conf/get_school_data.js')
var update_information_func = require('../conf/update_information.js')
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
    }else if(ip.includes('68.186')){
        name = '햇갯님'
    }
    fun_all.console_all ( `${name}이 서버를 접속하셨습니다.` );
    next();
})

app.listen(port, function(){
    console.log(`${port}포트로 서버 실행`);
});
//TODO token_Data
app.post('/receive_token_inforation', (req, res) =>{
    token_data_func.receive_token_inforation(req,res);
})

//TODO get_school_data
app.get('/gsmschoolfood', (req, res) =>{
    get_school_func.gsmschoolfood(req, res);
})

app.get('/gsm_hire_list', (req, res) =>{
    get_school_func.gsm_hire_list(req, res);
})

app.get('/gsm_employment_rate', (req, res) =>{
    get_school_func.gsm_employment_rate(req, res);
})

//TODO login_register
app.post('/login_check', (req, res) =>{
    login_register_func.logincheck(req, res);
})

app.post('/emailCheck', (req,res) =>{
    login_register_func.emailCheck(req,res);
})

app.post('/insert_user_information', (req,res) =>{
    login_register_func.insert_user_information(req,res);
})

//TODO board
// app.post('/board', (req,res) => {
//     board_func.board(req,res);
// })

app.post('/get_board_information', (req,res) => {
    board_func.get_board_information(req,res);
})

app.post('/get_comment_information', (req, res) =>{
    board_func.get_comment_information(req,res);
})
app.post('/trash_Data', (req, res) =>{
    board_func.trash_Data(req, res);
})

app.post('/write_Bulletin', (req, res) =>{
    board_func.write_Bulletin(req, res);
})

app.post('/write_comment', (req, res) =>{
    board_func.write_comment(req, res);
})

app.post('/delete_board', (req,res) =>{
    board_func.delete_board(req, res);
})

app.post('/delete_comment', (req,res) =>{
    board_func.delete_comment(req, res);
})

app.post('/check_writer', (req,res) =>{
    board_func.check_writer(req, res);
})

app.post('/get_one_board', (req,res) =>{
    board_func.get_one_board(req, res);
})

app.post('/board_num', (req, res) =>{
    board_func.board_num(req, res);
})

app.post('/isgoodCheck', (req, res) =>{
    board_func.isgoodCheck(req, res);
})

app.post('/isgood', (req, res) =>{
    board_func.isgood(req, res);
})

app.post('/isgood_num', (req, res) =>{
    board_func.isgood_num(req, res);
})
app.post('/iungood', (req, res) =>{
    board_func.iungood(req, res);
})
//TODO update_information
app.post('/update_nickname', (req, res) =>{
    update_information_func.update_nickname(req, res);
})

app.post('/get_user_data', (req, res) =>{
    update_information_func.get_user_data(req, res);
})
//* end
//TODO----------------------------------------------------------------------------------------------------------------------
client.on('ready', () => {
    console.log(`Logged in as ${client.user.tag}!`);
});



client.login(config.token)

