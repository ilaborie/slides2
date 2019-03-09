

export function defer(delay = 0) {
    return fun => setTimeout(fun, delay);
}

export const plop = () => console.log('plop');
