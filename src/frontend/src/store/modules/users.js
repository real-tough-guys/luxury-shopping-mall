import axios from 'axios';

const config = {
    baseUrl: '/api/users/'
};
const state = {
    jwt: null,
    details: null
};
const getters = {
    getJwt: (state) => {
        return state.jwt;
    }
};
const mutations = {
    removeJwt: (state) => {
        state.jwt = null;
    },
    setJwt: (state, jwt) => {
        state.jwt = jwt;
    },
    setUserDetails(state, data) {
        state.details = data;
    }
};


const actions = {
    login({commit}, userSignupDto) {
        return new Promise((resolve, reject) => {
            axios.post(`${config.baseUrl}login`, userSignupDto)
                .then(response => {
                    commit("setJwt", response.data);
                    alert("로그인 되었습니다.");
                    console.log(response.data);
                })
                .catch(error => {
                    alert(error.response.data);
                    console.error(error)
                })
        })
    },

    signUp({commit}, userSignupDto) {
        return new Promise((resolve, reject) => {
            axios.post(`${config.baseUrl}`, userSignupDto)
                .then(response => {
                    alert("회원가입 성공!");
                    window.open("/login", "_self");
                })
                .catch(res => {
                    alert(res.response.data);
                });
        })
    },
    isDuplicatedEmail({commit}, email) {
        return new Promise((resolve, reject) => {
            axios.get(`${config.baseUrl}emails/${email}/exists`)
                .then(response => {
                    alert(response.data);
                    resolve(response);
                    console.log(response);
                })
                .catch(error => {
                    alert(error.response.data);
                    console.log(error);
                })
        })
    },
    details({commit}, id) {
        return new Promise((resolve, reject) => {
            axios.get(`${config.baseUrl}${id}/details`)
                .then(response => {
                    resolve(response)
                    console.log(response)
                })
                .catch(error => {
                    reject(error)
                    console.error(error)
                })
        })
    },
    detail({commit}) {
        return new Promise((resolve, reject) => {
            axios.get(`${config.baseUrl}/details`)
                .then(response => {
                    resolve(response)
                    console.log(response)
                })
                .catch(error => {
                    reject(error)
                    console.error(error)
                })
        })
    },
    deleteUser({commit}, id) {
        return new Promise((resolve, reject) => {
            axios.post(`${config.baseUrl}${id}/delete`)
                .then(response => {
                    resolve(response)
                    console.log(response)
                })
                .catch(error => {
                    reject(error)
                    console.error(error)
                })
        })
    },
    delUser({commit}) {
        return new Promise((resolve, reject) => {
            axios.post(`${config.baseUrl}/delete`)
                .then(response => {
                    resolve(response)
                    console.log(response)
                })
                .catch(error => {
                    reject(error)
                    console.error(error)
                })
        })
    },
    update({commit}, userUpdateDto) {
        return new Promise((resolve, reject) => {
            axios.post(`${config.baseUrl}update`, userUpdateDto)
                .then(response => {
                    resolve(response)
                    console.log(response)
                })
                .catch(error => {
                    reject(error)
                    console.error(error)
                })
        })
    },
    findAll({commit}) {
        return new Promise((resolve, reject) => {
            axios.get(`${config.baseUrl}list`)
                .then(response => {
                    resolve(response)
                    console.log(response)
                })
                .catch(error => {
                    reject(error)
                    console.error(error)
                })
        })
    }
};

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}