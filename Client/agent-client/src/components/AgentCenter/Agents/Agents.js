import React from 'react';

class Agents extends React.Component {

    render() {
        return (
            <div className="row">
                <div className="col-4">
                    <h2>Agent Types</h2>
                    <hr />
                    [POST 192.168.0.20:8080/WarAT2020/rest/client/agents/classes]
                    [DELETE 192.168.0.20:8080/WarAT2020/rest/client/agents/running/..aid..]
                    <br />
                    <h2>Start Agent</h2>
                    <hr />
                    [PUT 192.168.0.20:8080/WarAT2020/rest/client/agents/running/..type../..name..]
                </div>
                <div className="col-8">
                    <h2>Running Agents</h2>
                    <hr />
                    [POST 192.168.0.20:8080/WarAT2020/rest/client/agents/running]
                </div>
            </div>
        );

    }
}

export default Agents;