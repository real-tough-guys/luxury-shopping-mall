<template>
  <div>
    <v-card max-width="500" max-height="500" class="mx-auto">
      <v-carousel>
        <v-carousel-item
          v-for="(item, i) in items"
          :key="i"
          :src="item.src"
          reverse-transition="fade-transition"
          transition="fade-transition"
        ></v-carousel-item>
      </v-carousel>
    </v-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          src:
            "https://image.musinsa.com/images/prd_img/2021021921333500000079112.jpg"
        },
        {
          src:
            "https://image.musinsa.com/images/prd_img/2021021921333500000011944.jpg"
        },
        {
          src:
            "https://image.musinsa.com/images/prd_img/2021021921333600000035549.jpg"
        },
        {
          src:
            "https://image.musinsa.com/images/prd_img/2021021921333600000098070.jpg"
        }
      ]
    };
  }
};
</script>
<style>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: 0.5;
  position: absolute;
  width: 100%;
}
</style>
