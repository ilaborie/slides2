const puppeteer = require('puppeteer');

(async () => {
    const browser = await puppeteer.launch();
    const page = await browser.newPage();
    const data = require('./slides2-kt/public/data.json');
    // console.log({data});
    for (const pres of data) {
        for (const instance of pres.instances) {
            // console.log({instance});
            const base = `slides2-kt/public/${instance.path}/index-${instance.label}`;
             await page.goto(`file://${__dirname}/${base}.html`, {waitUntil: 'networkidle2'});

            // await page.goto(`http://localhost:1234/${instance.path}/index-${instance.label}.html`, {waitUntil: 'networkidle2'});

            try {
                const path = `./${base}.png`;
                await page.screenshot({path});
                console.log('📸', path);
            } catch(e) {
                console.error(e);
            }
            
            try {
                const path = `./${base}.pdf`;
                await page.pdf({path, format: 'A4'});
                console.log('📇', path)
            } catch(e) {
                console.error(e);
            }
        }
    }

    await browser.close();
})();