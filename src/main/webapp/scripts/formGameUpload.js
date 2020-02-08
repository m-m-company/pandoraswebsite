let tags;
const getTags = () => {
    $.ajax({
        type: 'GET',
        url: "/getTags",
        success: (data) => {
            tags = data
        },
        async: false
    })
};

linkSize = 1;
tagSize = 1;
getTags();

$(document).ready(function () {
    for(let i = 0; i < tags.length; ++i){
        let id = "tag-".concat((i+1).toString());
        let row = document.createElement("div");
        row.classList.add("custom-control");
        row.classList.add("custom-checkbox");
        row.classList.add("custom-control-inline");
        let tag = document.createElement("input");
        tag.type = "checkbox";
        tag.classList.add("custom-control-input");
        tag.id = id;
        tag.name = id;
        tag.value = tags[i];
        let label = document.createElement("label");
        label.classList.add("custom-control-label");
        label.htmlFor = "tag-".concat((i+1).toString());
        label.innerHTML = tags[i];
        row.appendChild(tag);
        row.appendChild(label);
        document.getElementById("tags").appendChild(row);
    }
});

function addRow(event) {
    let actualRow = $(event.target.parentNode.parentNode);
    let newRow = actualRow.clone();
    if (event.target.id === "btn-add-link") {
        ++linkSize;
        let newInputText = newRow[0].children[0].children[0];
        newInputText.value = "";
        let oldInputText = actualRow[0].children[0].children[0];
        let actualName = $(oldInputText).attr("name");
        let id = (Number(actualName.split("-")[1]) + 1).toString();
        $(newInputText).attr("name", "link-".concat(id));
        newRow.insertAfter(actualRow);
        reformatName(newRow, true);
    } else if (event.target.id === "btn-add-tag") {
        ++tagSize;
        let newInputTag = newRow[0].children[0].children[0];
        let oldInputTag = actualRow[0].children[0].children[0];
        let actualName = $(oldInputTag).attr("name");
        let id = (Number(actualName.split("-")[1]) + 1).toString();
        $(newInputTag).attr("name", "tag-".concat(id));
        newRow.insertAfter(actualRow);
        reformatName(newRow, true);
    }
}

function removeRow(event) {
    if ((event.target.id === "btn-remove-link" && linkSize > 1) || (event.target.id === "btn-remove-tag" && tagSize > 1)) {
        let actualRow = $(event.target.parentNode.parentNode);
        if (event.target.id === "btn-remove-link") {
            reformatName(actualRow, false);
            --linkSize;
        } else if (event.target.id === "btn-remove-tag") {
            reformatName(actualRow, false);
            --tagSize;
        }
        console.log(actualRow[0].children[0].children[0]);
        actualRow.remove();
    }
}

function reformatName(actualRow, up) {
    let nextRow = actualRow[0].nextSibling;
    if (nextRow != null) {
        while (nextRow.nextSibling !== null) {
            let inputText = nextRow.children[0].children[0];
            let name = $(inputText).attr("name").split("-")[0];
            name.concat("-");
            let id = Number($(inputText).attr("name").split("-")[1]);
            console.log(id);
            if (up) {
                ++id;
            } else {
                --id;
            }
            $(inputText).attr("name", name.concat(String(id)));
            nextRow = nextRow.nextSibling;
        }
    }
}