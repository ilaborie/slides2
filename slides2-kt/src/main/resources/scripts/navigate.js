import {keymap, navigateTo} from './slide';

// XXX With Firefox, we can smooth the scrolling
const isFirefox = typeof InstallTrigger !== 'undefined';
if (isFirefox) {
    const main = document.querySelector('main');
    if (main) {
        // scroll-behavior: smooth
        main.style.scrollBehavior = 'smooth';
    }
}

// Navigation
const prevSelector = "section:target .previous a";
const nextSelector = "section:target .next a";

const home = () => navigateTo("section.cover");

// const previousSlide = () => navigateTo(prevSelector);
// const nextSlide = () => navigateTo(nextSelector);

const nextStep = () => {
    const currentSlide = document.querySelector("section:target");
    if (!currentSlide) {
        // assume we are on the first slide
        navigateTo('section .next a');
        return;
    }

    const currentStepSlide = document.querySelector('section.steps:target');
    if (currentStepSlide) {
        const current = currentStepSlide.querySelector('.step-current');
        const next = currentStepSlide.querySelector(!current ? '.step' : '.step:not(.step-done):not(.step-current)');
        // console.debug('nextStep', {current, next});
        if (current) {
            current.classList.toggle('step-current');
            current.classList.toggle('step-done');
        }
        next ? next.classList.toggle('step-current') : navigateTo(nextSelector);
        return
    }
    // no step, go to next slide
    navigateTo(nextSelector)
};
const previousStep = () => {
    const currentStepSlide = document.querySelector('section.steps:target');
    if (currentStepSlide) {
        const current = currentStepSlide.querySelector('.step-current');
        const done = currentStepSlide.querySelectorAll('.step-done');
        const previous = done && done[done.length - 1];
        // console.debug('previousStep', {current, previous});
        if (current) {
            current.classList.toggle('step-current');
        }
        if (previous) {
            previous.classList.toggle('step-current');
            previous.classList.toggle('step-done');
        } else {
            navigateTo(prevSelector);
        }
    } else {
        // no step, go to next slide
        navigateTo(prevSelector)
    }
};

// Register handlers
keymap({
    ArrowRight: nextStep,
    ArrowLeft: previousStep,
    Space: nextStep,
    Home: home
});

// FIXME Current page (hack for Chrome ???)

// setTimeout(() => navigateTo(document.location.hash), 1000);
navigateTo(`a[href='${document.location.hash}']`);
