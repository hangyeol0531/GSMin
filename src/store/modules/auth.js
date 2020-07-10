import axios from 'axios'
import { router } from '../../router/index.js'

const state = {
    userInfo: null,
    token : localStorage.getItem('token')
}

const getters = {

}

const mutations = {
    login(state, payload) {
        localStorage.setItem('token', payload)
        state.token = payload
    },
    getUserInfo(state, payload) {
        state.userInfo = payload
    }

}

const actions = {
    async login({commit}, {email, pw}) {
        try{
            const resData = await axios.post('/login_check', {
                email : email,
                pw : pw
            })
            if(resData.data === null) alert("아이디 또는 비밀번호를 확인해주세요.")
            else commit('login', resData.data.token)
            axios.defaults.headers.common['Authorization'] = resData.data.token
            router.push({name : 'Home'})
        } catch(e) {
            console.log(e)
        }
    },

    async getUserInfo({commit}) {
        let getToken = localStorage.getItem('token')
        try {
            const resData =  await axios.post('/receive_token_inforation', {
                    user_token : getToken
                })
            let userInfo = {
                user_nickname: resData.data.nickname,
                user_grade: resData.data.grade
            }
            commit('getUserInfo', userInfo)
        } catch (e) {
            console.log(e)
        }
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}