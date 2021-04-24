import axios from "axios";
const state = {
  productList: null
};
const getters = {};
const actions = {
  getProductsList({ commit }) {
    return axios
      .get("api/products")
      .then(data => {
        commit("setProductList", data);
      })
      .catch(err => {
        console.log(err);
      });
  }
};

const mutations = {
    setProductList(state, data) {
        state.productList = data;
    },
};
export default {
  namespaced: true,
  getters,
  state,
  actions,
  mutations
};
