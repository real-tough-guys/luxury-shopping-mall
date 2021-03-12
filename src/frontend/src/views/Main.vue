<template>
  <div>
    <v-card class="mx-auto overflow-hidden" height="auto">
      <v-container fluid>
        <v-row dense>
          <v-col v-for="n in 8" :key="n" cols="12" sm="4">
            <v-hover v-slot="{ hover }">
              <v-card class="mx-auto" color="white lighten-4" max-width="344">
                <v-img
                  width="344"
                  height="344"
                  src="https://image.musinsa.com/images/prd_img/2021021921333500000079112.jpg"
                >
                  <v-expand-transition>
                    <div
                      v-if="hover"
                      class="d-flex transition-fast-in-fast-out blue-grey darken-2 v-card--reveal display-3 white--text"
                      style="height: 100%;"
                    >
                      $14.99
                    </div>
                  </v-expand-transition>
                </v-img>
                <v-card-text class="pt-6" style="position: relative;">
                  <v-btn
                    absolute
                    color="black"
                    class="white--text"
                    fab
                    large
                    right
                    top
                  >
                    <v-icon>mdi-cart</v-icon>
                  </v-btn>
                  <div class="font-weight-light grey--text title mb-2">
                    For the perfect meal
                  </div>
                  <h3 class="display-1 font-weight-light orange--text mb-2">
                    QW cooking utensils
                  </h3>
                  <div class="font-weight-light title mb-2">
                    Our Vintage kitchen utensils delight any chef.<br />
                    Made of bamboo by hand
                  </div>
                </v-card-text>
              </v-card>
            </v-hover>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </div>
</template>

<script>
export default {
  data: {}
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
