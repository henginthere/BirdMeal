<template>

    <!-- <div class="box">
      <div class="cat">
        <div class="cat__body"></div>
        <div class="cat__body"></div>
        <div class="cat__tail"></div>
        <div class="cat__head"></div>
      </div>
    </div>
    <blockquote class="info"></blockquote> -->
    <div >
      <Vue3Lottie :animationData="loadingJson" :height="600" :width="600" />
    </div>

</template>

<script>
  import { Vue3Lottie } from 'vue3-lottie'
  import 'vue3-lottie/dist/style.css'
  import loadingJson from '@/guideImg/loading.json'
export default {
  components: {
    Vue3Lottie,
  },
  data(){
    return {
      loadingJson,
    }
  }

};
</script>

<style lang="scss" scoped>
@mixin fill-full($dir: "full", $type: absolute) {
  position: $type;
  @if $dir != "bottom" {
    top: 0;
  }
  @if $dir != "right" {
    left: 0;
  }
  @if $dir != "left" {
    right: 0;
  }
  @if $dir != "top" {
    bottom: 0;
  }
}

.cat {
  position: relative;
  width: 100%;
  max-width: 20em;
  overflow: hidden;
  background-color:transparent;

  &::before {
    content: "";
    display: block;
    padding-bottom: 100%;
  }

  &:hover > * {
    animation-play-state: paused;
  }
  &:active > * {
    animation-play-state: running;
  }
}

%cat-img {
  @include fill-full;
  animation: rotating 2.79s cubic-bezier(0.65, 0.54, 0.12, 0.93) infinite;

  &::before {
    content: "";
    position: absolute;
    width: 50%;
    height: 50%;
    background-size: 200%;
    background-repeat: no-repeat;
    background-image: url("../guideImg/cattttpng.png");
  }
}

.cat__head {
  @extend %cat-img;

  &::before {
    top: 0;
    right: 0;
    background-position: 100% 0%;
    transform-origin: 0% 100%;
    transform: rotate(83deg);
  }
}

.cat__tail {
  @extend %cat-img;
  animation-delay: 0.2s;

  &::before {
    left: 0;
    bottom: 0;
    background-position: 0% 100%;
    transform-origin: 100% 0%;
    transform: rotate(-20deg);
  }
}

.cat__body {
  @extend %cat-img;
  animation-delay: 0.1s;

  &:nth-of-type(2) {
    animation-delay: 0.2s;
  }

  &::before {
    right: 0;
    bottom: 0;
    background-position: 100% 100%;
    transform-origin: 0% 0%;
  }
}

@keyframes rotating {
  from {
    transform: rotate(720deg);
  }
  to {
    transform: none;
  }
}

.box {
  display: flex;
  flex: 1;
  flex-direction: column;
  justify-content: flex-start;
  justify-content: center;
  align-items: center;
  background-color:transparent;
}

// reset

*,
*::before,
*::after {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html {
  height: 100%;
}

body {
  display: flex;
  flex-direction: column;
  min-height: 100%;
  margin: 0;
  line-height: 1.4;
}

.intro {
  width: 90%;
  max-width: 36rem;
  padding-bottom: 1rem;
  margin: 0 auto 1em;
  padding-top: 0.5em;
  font-size: calc(1rem + 2vmin);
  text-transform: capitalize;
  border-bottom: 1px dashed rgba(#000, 0.3);
  text-align: center;

  small {
    display: block;
    opacity: 0.5;
    font-style: italic;
    text-transform: none;
  }
}

.info {
  margin: 0;
  padding: 1em;
  font-size: 0.9em;
  font-style: italic;
  font-family: serif;
  text-align: right;
  opacity: 0.5;

  a {
    color: inherit;
  }
}
</style>
