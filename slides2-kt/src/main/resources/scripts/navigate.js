import {keymap} from './keymap';

export const navigateTo = (selector) => {
    const elt = document.querySelector(selector);
    if (elt) {
        elt.click();
    }
};

// XXX With Firefox, we can smooth the scrolling
const isFirefox =  typeof InstallTrigger !== 'undefined';
if (isFirefox) {
    const main = document.querySelector('main');
    if(main) {
        // scroll-behavior: smooth
        main.style.scrollBehavior = 'smooth';
    }
}

// Navigation
const prevSelector = "section:target .previous a";
const nextSelector = "section:target .next a";

const previousSlide = () => navigateTo(prevSelector);
const nextSlide = () => navigateTo(nextSelector);
const home = () => navigateTo("section.cover");
const nextStep = () => {
    const currentStepSlide = document.querySelector('section.steps:target');
    if (currentStepSlide) {
        const current = currentStepSlide.querySelector('.step-current');
        const next = currentStepSlide.querySelector(!current ? '.step' : '.step-current ~ .step');
        if (next) {
            if (current) {
                current.classList.toggle('step-current');
                current.classList.toggle('step-done');
            }
            next.classList.toggle('step-current');
        } else {
            // no more step, go to next slide
            navigateTo(nextSelector)
        }
    } else {
        // no step, go to next slide
        navigateTo(nextSelector)
    }
};

// Register handlers
keymap({
    ArrowRight: nextStep,
    ArrowLeft: previousSlide,
    Space: nextStep,
    Home: home
});

// FIXME Current page (hack for Chrome ???)

// setTimeout(() => navigateTo(document.location.hash), 1000);
navigateTo(`a[href='${document.location.hash}']`);
