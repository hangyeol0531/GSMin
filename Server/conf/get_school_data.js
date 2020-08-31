const config = require('../../config.json');
const db = require('../conf/config_database.js')
var fs = require('fs');    
const axios = require('axios');
const School = require('node-school-kr');
const school = new School()
school.init(School.Type.HIGH, School.Region.GWANGJU, 'F100000120');


exports.gsmschoolfood = async (req, res) =>{
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
}

exports.gsm_hire_list = async (req,res) =>{
    const job_list = await axios.get('https://sheet.best/api/sheets/a954e6a3-0e4f-48a6-8a20-234ff289fffd');
    const spread_list = job_list[6]
    res.end(JSON.stringify(job_list.data.slice(6)));
    // var data = fs.readFileSync('../emplist','utf-8');   //!임시
    // res.end(data) //!임시
}

exports.gsm_employment_rate = async (req,res) =>{
    // const employment_rate = await axios.get('https://sheet.best/api/sheets/a2e05329-b7f6-4824-8295-eaa9ba36e5bd');
    // const employment_rate_data = employment_rate.data.slice(5, -1)
    // let emp_num;
    // for (var i = 0; i < 80; i++){
    //     if(!(employment_rate_data[i]['2'])){
    //         emp_num = i;
    //         break;
    //     }
    // }

    emp_num = 12; // !테스트
    send_data = new Object;
    send_data.rate = emp_num * 1.25
    send_data.emp_num = emp_num
    res.end(JSON.stringify(send_data));
}