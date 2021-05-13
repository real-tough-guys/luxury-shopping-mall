<template>
  <v-main class="main">
    <v-container class="privacy-wrap">
      <span style="font-size: 50px" class="mx-10">My Page</span>
      <v-container class="information-wrap">
        <v-avatar size="150">
          <v-img src="https://randomuser.me/api/portraits/men/78.jpg" />
        </v-avatar>
        <div class="privacy">
          <span class="user-name">{{ this.$store.state.users.nickname }}</span>
          <br />

          <router-link to="/editUser" style="color:white;">
          <button>

              회원정보 변경

          </button>
          </router-link>

          <span class="hidden-xs-only">&nbsp;/&nbsp;</span>
          <button>로그아웃</button>
          <br />
          <span>가입일: 2020.03.11</span>
        </div>
      </v-container>
    </v-container>
    <v-container class="order-status-wrap">
      <span style="font-size: 30px">주문내역 조회</span>
      <v-data-table
        :headers="headers"
        :items="products"
        :items-per-page="5"
        class="elevation-2"
      />
    </v-container>
  </v-main>
</template>
<script>
export default {
  data() {
    return {
      userName: "김철수",
      headers: [
        { text: "상품정보", value: "name" },
        { text: "주문번호", value: "orderId" },
        { text: "주문일자", value: "orderDate" },
        { text: "주문금액", value: "orderAmountAndQuantity" },
        { text: "주문상태", value: "orderStatus" }
      ],
      products: [
        {
          name: "Frozen Yogurt",
          orderDate: "2021.03.11",
          orderId: 1,
          orderAmountAndQuantity: 20000,
          orderStatus: "상품준비"
        },
        {
          name: "Ice cream sandwich",
          orderDate: "2021.03.11",
          orderId: 2,
          orderAmountAndQuantity: 40000,
          orderStatus: "배송완료"
        },
        {
          name: "Eclair",
          orderDate: "2021.03.11",
          orderId: 3,
          orderAmountAndQuantity: 10000,
          orderStatus: "배송중"
        },
        {
          name: "Cupcake",
          orderDate: "2021.03.11",
          orderId: 4,
          orderAmountAndQuantity: 90000,
          orderStatus: "배송중"
        },
        {
          name: "Gingerbread",
          orderDate: "2021.03.11",
          orderId: 5,
          orderAmountAndQuantity: 120000,
          orderStatus: "배송중"
        },
        {
          name: "Jelly bean",
          orderDate: "2021.03.12",
          orderId: 6,
          orderAmountAndQuantity: 11120000,
          orderStatus: "상품준비"
        },
        {
          name: "Lollipop",
          orderDate: "2021.03.13",
          orderId: 7,
          orderAmountAndQuantity: 20000,
          orderStatus: "배송완료"
        },
        {
          name: "Honeycomb",
          orderDate: "2021.03.14",
          orderId: 8,
          orderAmountAndQuantity: 10000,
          orderStatus: "배송중"
        },
        {
          name: "Donut",
          orderDate: "2021.03.15",
          orderId: 9,
          orderAmountAndQuantity: 2000,
          orderStatus: "배송중"
        },
        {
          name: "KitKat",
          orderDate: "2021.03.11",
          orderId: 10,
          orderAmountAndQuantity: 20000,
          orderStatus: "배송중"
        }
      ]
    };
  }
};
</script>
<style scoped>
.main {
  font-family: "Do Hyeon", sans-serif;
  font-weight: 100;
}
.privacy-wrap {
  max-width: 100%;
  background-color: black;
  color: white;
}
.information-wrap {
  display: flex;
  flex-direction: row;
  margin-top: 15px;
}
.privacy {
  margin-left: 20px;
  font-size: 20px;
}
.user-name {
  font-size: 3rem;
  letter-spacing: 7px;
}
.order-status-wrap {
  max-width: 100%;
}
</style>
