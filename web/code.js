/* jshint esversion: 6 */
document.addEventListener('DOMContentLoaded', function() {

    document.getElementById('searchbutton').onclick = () => {
        fetch("/search?q=" + document.getElementById('searchbox').value)
        .then((response) => response.json())
        .then((data) => {
            document.getElementById("responsesize").innerHTML = 
                "<p>" + data.length + " websites retrieved</p>";
            let results = data.map((page) =>
                `<li><a class="content-anchor" href="${page.url}">${page.title}</a></li>`)
                .join("\n");
            document.getElementById("urllist").innerHTML = `<ul>${results}</ul>`;
        });
    };


});
