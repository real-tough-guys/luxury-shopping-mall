import axios from "axios";

const config = {
    baseUrl: "/api/users/"
};
const state = {
    jwt: null,
    id: null,
    details: null,
    nickname: "로그인하세요.",
    userList: []
};
const mutations = {
    setJwt: (state, jwt) => {
        state.jwt = jwt;
    },
    setId: (state, id) => {
        state.id = id;
    },
    setUserDetails: (state, data) => {
        state.details = data;
        state.nickname = data.nickname;
    },
    setUserList: (state, data) => {
        state.userList = data;
    },
    logout: state => {
        state.jwt = null;
        state.details = null;
        state.id = null;
        state.nickname = "로그인하세요.";
    }
};

const actions = {
    async login({state, commit}, userSignupDto) {
        let isSuccess = true;
        await axios
            .post(`${config.baseUrl}login`, userSignupDto)
            .then(response => {
                commit("setJwt", response.data.token);
                commit("setId", response.data.id);
                alert("로그인 되었습니다.");
                console.log(state.jwt);
            })
            .catch(error => {
                alert(error.response.data);
                console.error(error);
                isSuccess = false;
            });
        return isSuccess;
    },

    async logout({commit}) {
        let isSuccess = true;

        if (state.jwt != null) {
            commit("logout");
            alert("로그아웃 되었습니다.");
        } else {
            alert("로그인 되어있지 않습니다.");
            isSuccess = false;
        }
        return isSuccess;
    },

    async signUp({commit}, userSignupDto) {
        let isSuccess = true;
        await axios
            .post(`${config.baseUrl}`, userSignupDto)
            .then(response => {
                alert("회원가입 성공!");
                console.log(response.data);
            })
            .catch(error => {
                alert(error.response.data);
                console.log(error);
                isSuccess = false;
            });
        return isSuccess;
    },
    isDuplicatedEmail({commit}, email) {
        return new Promise((resolve, reject) => {
            axios
                .get(`${config.baseUrl}emails/${email}/exists`)
                .then(response => {
                    if (response.data) {
                        alert("중복되지 않은 이메일입니다.");
                    } else {
                        alert("중복된 이메일입니다.");
                    }
                    resolve(response);
                    console.log(response);
                })
                .catch(error => {
                    alert(error.response.data);
                    console.log(error);
                });
        });
    },
    async details({commit}, id) {
        let isSuccess = true;
        await axios
            .get(`${config.baseUrl}${id}/details`, {
                headers: {Authorization: `Bearer ${state.jwt}`}
            })
            .then(response => {
                commit("setUserDetails", response.data);
                console.log(response);
            })
            .catch(error => {
                alert(error.response.data);
                console.error(error);
                isSuccess = false;
            });
        return isSuccess;
    },
    async deleteUser({commit}) {
        let isSuccess = true;
        await axios
            .delete(`${config.baseUrl}${state.details.id}/delete`, {
                headers: {Authorization: `Bearer ${state.jwt}`}
            })
            .then(response => {
                commit("logout");
                alert("계정을 삭제했습니다.");
                console.log(response);
            })
            .catch(error => {
                alert(error.response.data);
                console.error(error);
                isSuccess = false;
            });
        return isSuccess;
    },
    async deleteUserWithId({commit}, id) {
        let isSuccess = true;
        await axios
            .delete(`${config.baseUrl}${id}/delete`, {
                headers: {Authorization: `Bearer ${state.jwt}`}
            })
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                alert(error.response.data);
                console.error(error);
                isSuccess = false;
            });
        return isSuccess;
    },

    async update({commit}, userUpdateDto) {
        let isSuccess = true;
        await axios
            .put(`${config.baseUrl}update`, userUpdateDto, {
                headers: {Authorization: `Bearer ${state.jwt}`}
            })
            .then(response => {
                alert("계정을 수정했습니다. 다시 로그인하세요");
                commit("logout");
                console.log(response);
            })
            .catch(error => {
                alert(error.response.data);
                console.error(error);
                isSuccess = false;
            });
        return isSuccess;
    },
    async findAll({commit}) {
        let isSuccess = true;
        await axios
            .get(`${config.baseUrl}list`, {
                headers: {Authorization: `Bearer ${state.jwt}`}
            })
            .then(response => {
                console.log(response);
                commit("setUserList", response.data);
            })
            .catch(error => {
                console.error(error);
                alert(error.response.data);
                isSuccess = false;
            });
        return isSuccess;
    }
};
export default {
    namespaced: true,
    state,
    mutations,
    actions
};