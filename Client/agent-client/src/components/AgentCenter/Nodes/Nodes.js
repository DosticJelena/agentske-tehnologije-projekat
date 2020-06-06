import React from 'react';

class Nodes extends React.Component {

    render() {
        return (
            <div className="row">
                <div className="col-4">
                    <h2>Master Node Info</h2>
                    <hr />
                    <div className="this-node-info">
                        <p>IP Address: <span>192.168.0.20</span></p>
                        <p>Port: <span>8080</span></p>
                        <p>Alias: <span>desktop</span></p>
                    </div>
                    <br />
                    <h2>This Node Info <h4>[master]</h4></h2>
                    <hr />
                    <div className="this-node-info">
                        <p>IP Address: <span>192.168.0.20</span></p>
                        <p>Port: <span>8080</span></p>
                        <p>Alias: <span>desktop</span></p>
                    </div>
                </div>
                <div className="col-8">
                    <h2>All Nodes</h2>
                    <hr />
                    <div className="all-nodes">
                        <table>
                            <tr><td>IP Address</td><td>Port</td><td>Alias</td></tr>
                            <tr><td>192.168.0.33</td><td>8080</td><td>desktop2</td></tr>
                            <tr><td>192.168.0.40</td><td>8080</td><td>desktop3</td></tr>
                        </table>
                    </div>
                </div>
            </div>
        );

    }
}

export default Nodes;