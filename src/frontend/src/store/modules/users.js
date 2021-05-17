import axios from 'axios';

const config = {
    baseUrl: '/api/users/'
};
const state = {
    jwt: null,
    details: null,
    nickname: "로그인하세요."
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
    setUserDetails: (state, data) => {
        state.details = data;
        state.nickname = data.nickname;
    },
    logout: (state) => {
        state.jwt = null;
        state.details = null;
        state.nickname = "로그인하세요.";
    }
};


const actions = {
        async login({commit}, userSignupDto) {
            let isSuccess = true
            await axios.post(`${config.baseUrl}login`, userSignupDto)
                .then(response => {
                    commit("setJwt", response.data.token);
                    alert("로그인 되었습니다.");
                    console.log(response.data);
                })
                .catch(error => {
                    alert(error.response.data);
                    console.error(error)
                    isSuccess = false;
                })
            return isSuccess
        },

        logout({commit}) {
            return new Promise((resolve, reject) => {
                axios.get(`${config.baseUrl}logout`,
                    {headers: {Authorization: `Bearer ${state.jwt}`}})
                    .then(response => {
                        commit("logout");
                        alert("로그아웃 되었습니다.");
                        console.log(response.data);
                    })
                    .catch(error => {
                        alert(error.response.data);
                        console.error(error)
                    })
            })
        },

        async signUp({commit}, userSignupDto) {
            let isSuccess = true;
            await axios.post(`${config.baseUrl}`, userSignupDto)
                .then(response => {
                    alert("회원가입 성공!");
                    console.log(response.data);
                })
                .catch(error => {
                    alert(error.response.data);
                    console.log(error);
                    isSuccess = false;
                })
            return isSuccess;
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
        async details({commit}, id) {
            let isSuccess = true;
            await axios.get(`${config.baseUrl}${id}/details`)
                .then(response => {
                    commit("setUserDetails", response.data);
                    console.log(response)
                })
                .catch(error => {
                    alert(error.response.data)
                    console.error(error)
                    isSuccess = false;
                })
            return isSuccess
        },
        async detail({commit}) {
            let isSuccess = true;
            await axios.get(`${config.baseUrl}/details`,
                {headers: {Authorization: `Bearer ${state.jwt}`}})
                .then(response => {
                    commit("setUserDetails", response.data);
                    console.log(response)
                })
                .catch(error => {
                    alert(error.response.data)
                    console.error(error)
                    isSuccess = false;
                })
            return isSuccess
        }
        ,
        async deleteUser({commit}) {
            let isSuccess = true;
            await axios.delete(`${config.baseUrl}${state.details.id}/delete`,
                {headers: {'Authorization': `Bearer ${state.jwt}`}})
                .then(response => {
                    commit("logout")
                    alert(response.data)
                    console.log(response)
                })
                .catch(error => {
                    alert(error.response.data)
                    console.error(error)
                    isSuccess = false;
                })
            return isSuccess;
        }
        ,
        delUser({commit}) {
            return new Promise((resolve, reject) => {
                axios.post(`${config.baseUrl}delete`,
                    {headers: {'Authorization': `Bearer ${state.jwt}`}})
                    .then(response => {
                        resolve(response)
                        alert(response.data)
                        commit("logout")
                        console.log(response)
                    })
                    .catch(error => {
                        reject(error)
                        alert(error.response.data)
                        console.error(error)
                    })
            })
        }
        ,
        async update({commit}, userUpdateDto) {
            let isSuccess = true;
            await axios.post(`${config.baseUrl}update`, userUpdateDto,
                {headers: {'Authorization': `Bearer ${state.jwt}`}})
                .then(response => {
                    alert(response.data)
                    commit("logout");
                    console.log(response)
                })
                .catch(error => {
                    alert(error.response.data)
                    console.error(error)
                    isSuccess = false;
                })
            return isSuccess;
        }
        ,
        findAll({commit}) {
            return new Promise((resolve, reject) => {
                axios.get(`${config.baseUrl}list`,
                    {headers: {'Authorization': `Bearer ${state.jwt}`}})
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
    }
;

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}