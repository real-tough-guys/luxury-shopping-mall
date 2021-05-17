import axios from 'axios';

const config = {
    baseUrl: '/api/carts/'
};
const state = {
    myCart: []
};
const getters = {
    getMyCart: (state) => {
        return state.myCart;
    }
};
const mutations = {
    setMyCart: (state, myCart) => {
        state.myCart = myCart;
    },
};


const actions = {
    async getMyCarts({commit}, userId) {
        await axios.get(`${config.baseUrl}list/${userId}`,
            {headers: {Authorization: `Bearer ${state.jwt}`}})
            .then(response => {
                commit("setMyCart", response.data);
                console.log(response.data);
            })
            .catch(error => {
                alert(error.response.data);
                console.error(error)
            })
    },
};

export default {
    namespaced: true,
    state,
    getters,
    mutations,
    actions
}