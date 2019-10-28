function main(){
    $("#dg").datagrid({
        url: `http://localhost:8080/indexPaginated`,
        method: 'get',
        onLoadSuccess: function (data) {
            console.log('w');
        }
    });
}

main();