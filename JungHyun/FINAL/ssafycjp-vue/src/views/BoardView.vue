<template>
    <div>
        <TheHeaderNav/>
        <div class="boardViewContainer">
            <div>
                <NavigationDrawer />
            </div>
            <div class="text-center">
            </div>
            <div class="innerContainer">
                <br><br><br>
                <RouterView />
            </div>
        </div>
    </div>
</template>
  
<script setup>
  import TheHeaderNav from '@/components/common/TheHeaderNav.vue';
  import NavigationDrawer from '@/components/common/NavigationDrawer.vue';
</script>
  
<style scoped>
.my-h2 {
    display: inline-block
}

.boardViewContainer {
    display: flex;
    flex-direction: row;
}

.innerContainer {
    position: relative;
    left: -260px;
    width: 100%;
}

</style>
  