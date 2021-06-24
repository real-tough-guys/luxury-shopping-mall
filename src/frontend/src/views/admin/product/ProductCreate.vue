<template>
  <div>
    <v-card max-width="450" max-height="auto" class="mx-auto my-12">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-card-title>
          <v-text-field
            color="primary"
            :rules="titleRules"
            required
            v-model="title"
            counter="15"
            label="Title"
            clearable
            clear-icon="mdi-close-circle"
            outlined
          ></v-text-field>
        </v-card-title>
        <v-card-title>
          <v-textarea
            color="primary"
            :rules="contentRules"
            v-model="content"
            counter="50"
            label="Content"
            clearable
            clear-icon="mdi-close-circle"
            outlined
          ></v-textarea>
        </v-card-title>

        <v-card-title>
          <v-text-field
            color="primary"
            v-model="price"
            label="Price"
            clearable
            clear-icon="mdi-close-circle"
            outlined
          ></v-text-field>
          원
        </v-card-title>

        <v-card-title>
          <v-select
            color="primary"
            :items="items"
            :rules="[v => !!v || 'category를 선택해주세요']"
            required
            v-model="category"
            :menu-props="{ top: true, offsetY: true }"
            label="Category"
            outlined
          ></v-select>
        </v-card-title>
        <v-card-title>
          <v-col cols="12">
            <v-combobox
              v-model="sizeSelect"
              :items="sizeItems"
              label="해당 상품의 사이즈를 선택해주세요!!"
              multiple
            ></v-combobox>
          </v-col>
        </v-card-title>
        <v-card-title>
          <v-col cols="12">
            <v-combobox
              v-model="colorSelect"
              :items="colorItems"
              label="해당 상품의 색상을 선택해주세요"
              multiple
            ></v-combobox>
          </v-col>
        </v-card-title>

        <v-card-subtitle>
          <h4>Image를 올려주세요</h4>
        </v-card-subtitle>
        <input
          type="file"
          ref="imageInput"
          name="images[]"
          id="photo"
          @change="imagesAdd"
          hidden
          multiple
        />

        <v-btn
          color="blue-grey"
          class="ma-2 white--text"
          @click="onClickImageUpload"
          >Imagae Upload
          <v-icon right dark>
            mdi-cloud-upload
          </v-icon>
        </v-btn>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-left">
                  Image
                </th>
                <th class="text-left">
                  Delete
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(img, i) in previewImage" v-bind:key="i">
                <th>
                  <v-img :src="img" width="130" height="130"></v-img>
                </th>
                <th>
                  <v-btn
                    x-small
                    dark
                    color="pink"
                    v-show="previewImage"
                    @click="removeImage(i)"
                  >
                    <v-icon dark>
                      mdi-delete
                    </v-icon>
                    Image {{ i + 1 }}
                  </v-btn>
                </th>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
        <v-card-subtitle>
          <v-checkbox
            v-model="checkbox"
            :rules="[v => !!v || '상품 거래에 동의 해주세요']"
            label="Do you agree?"
            required
          ></v-checkbox>
        </v-card-subtitle>
      </v-form>
      <v-btn
        color="blue-grey"
        block
        class=" white--text"
        :disabled="!valid"
        @click="validate"
      >
        Submit
        <v-icon right color="white">
          mdi-checkbox-marked-circle
        </v-icon>
      </v-btn>
    </v-card>
  </div>
</template>
<script>
import axios from "axios";

export default {
  data() {
    return {
      valid: true,
      titleRules: [
        v => !!v || "title is required",
        v => (v && v.length <= 15) || "Name must be less than 15 characters"
      ],
      contentRules: [
        v => !!v || "content is required",
        v => (v && v.length <= 50) || "50자 이하로 작성 부탁해용"
      ],
      checkbox: false,
      items: ["Top", "Bottom", "Outer"],
      images: {},
      previewImage: [],
      title: "",
      price: "",
      category: "",
      content: "",
      sizeSelect: [],
      sizeItems: ["S", "M", "L", "XL", "FREE"],
      colorSelect: [],
      colorItems: [
        "White",
        "Black",
        "Blue",
        "Red",
        "Grey",
        "Light-Blue",
        "Brown"
      ]
    };
  },
  methods: {
    validate() {
      this.$refs.form.validate();
      this.allSubmit();
    },

    onClickImageUpload() {
      this.$refs.imageInput.click();
    },
    imagesAdd(e) {
      var files = e.target.files || e.dataTransfer.files;
      this.images = [];
      Array.prototype.push.apply(this.images, files); //array element add
      if (!this.images.length) return;
      this.previewCreateImage(this.images);
    },
    previewCreateImage(file) {
      for (var i = 0; i < file.length; i++) {
        var reader = new FileReader();
        var vm = this;
        reader.onload = e => {
          vm.previewImage.push(e.target.result);
        };
        reader.readAsDataURL(file[i]);
      }
    },
    removeImage(key) {
      this.previewImage.splice(key, 1);
      this.images.splice(key, 1);
    },
    allSubmit() {
      var frmUploadImage = new FormData();
      const imageObj = [];
      for (var i = 0; i < this.images.length; i++) {
        frmUploadImage.append("files", this.images[i]);
        imageObj.push(this.images[i].name);
      }
      const productObj = {
        productName: this.title,
        productContent: this.content,
        productPrice: this.price,
        productCategory: this.category,
        productImageurl: imageObj,
        productSize: this.sizeSelect,
        productColor: this.colorSelect
      };
      axios
        .post("/api/products/file", frmUploadImage, {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        })
        .then(res => {
          console.log(res);
          axios
            .post("/api/products", productObj)
            .then(res => {
              this.$router.push({
                name: "AdminProductDetail",
                params: { id: res.data }
              });
            })
            .catch(err => {
              console.log(err);
            });
        })
        .catch(err => {
          console.log(err);
        });
    }
  }
};
</script>
