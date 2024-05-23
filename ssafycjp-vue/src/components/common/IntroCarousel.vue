<template>
  <v-carousel :show-arrows="false" height="600">
    <v-carousel-item
      v-for="(item, i) in items"
      :key="i"
      :src="item.src"
      cover
    ></v-carousel-item>
  </v-carousel>
</template>

<script>
import Main1 from '@/assets/Main1.jpg';
import Main2 from '@/assets/Main2.jpg';
import Main3 from '@/assets/Main3.jpg';

export default {
  data () {
    return {
      items: [
        {
          src: Main1,
        },
        {
          src: Main2,
        },
        {
          src: Main3,
        },
      ],
    }
  },
}
</script>

<style lang="scss" scoped>
</style>