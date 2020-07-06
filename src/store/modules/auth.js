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
    login({commit}, {email, pw}) {
        return axios.post('/login_check', {
            email : email,
            pw : pw
        })
            .then((response) => {
                commit('login', response.data.token)
                axios.defaults.headers.common['Authorization'] = response.data.token
                router.push({name : 'Home'})
            })
    },
    getUserInfo({commit}) {
        let getToken = localStorage.getItem('token')
        console.log(getToken)
        return axios.post('/receive_token_inforation', {
            user_token : getToken
        })
            .then((response) => {
                console.log(response)
                let userInfo = {
                    user_nickname: response.data.nickname,
                    user_grade: response.data.grade
                }
                commit('getUserInfo', userInfo)
            }).catch((e) => {
                alert(e)
            })
    }
}

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}