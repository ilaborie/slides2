
export function defer(timeout = 0) {
    return fun => setTimeout(fun, timeout);
}