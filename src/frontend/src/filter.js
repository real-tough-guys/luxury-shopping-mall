let myMixin = {

  filters: {
    loadImgOrPlaceholder: function(path) {
      return require("@/assets/images/" + path);
    },
    moneyFilter: function(value) {
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
  }
};
export default myMixin;
