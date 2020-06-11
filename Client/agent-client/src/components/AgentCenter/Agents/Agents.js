import React from 'react';
import axios from 'axios';

class Agents extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            runningAgents: [],
            agentClasses: []
        }
    }

    getRunningAgents = () => {
        axios.get(this.props.masterURL + "/client/agents/running")
            .then(response => {
                console.log(response);
                this.setState({
                    runningAgents: response.data
                })
            })
            .catch(error => {
                console.log(error);
            })
    }

    startAgentFunction = (type, name) => {
        axios.put(this.props.masterURL + "/client/agents/running/" + type + "/" + name)
            .then(response => {
                console.log(response);
                this.getRunningAgents();
            })
            .catch(error => {
                console.log(error);
            });
    }

    stopAgentFunction = (agent) => {
        var config = {
            headers: { 'Content-Type': 'application/json' },
            data: {
                host: agent.host,
                type: agent.type,
                name: agent.name
            }
        };
        axios.delete(this.props.masterURL + "/client/agents/running", config)
            .then(response => {
                console.log(response);
                this.getRunningAgents();
            })
            .catch(error => {
                console.log(error);
            });
    }

    componentDidMount() {
        axios.get(this.props.masterURL + "/client/agents/classes")
            .then(response => {
                console.log(response);
                this.setState({
                    agentClasses: response.data
                })
            })
            .catch(error => {
                console.log(error);
            });

        this.getRunningAgents();
    }

    render() {
        return (
            <div className="row">
                <div className="col-4">
                    <h2>Agent Types</h2>
                    <hr />
                    {this.state.agentClasses.map(agentClass => {
                        return <p>
                            {agentClass.module}
                            <button className="btn" onClick={() => this.startAgentFunction(agentClass.module, agentClass.module)}>Start</button>
                        </p>
                    })}
                    [POST 192.168.0.20:8080/WarAT2020/rest/client/agents/classes]
                    [DELETE 192.168.0.20:8080/WarAT2020/rest/client/agents/running/..aid..]
                    <br />
                </div>
                <div className="col-8">
                    <h2>Running Agents</h2>
                    <hr />
                    <div className="all-nodes">
                        <table>
                            <tr><td><strong>Agent Name</strong></td><td><strong>Host Address</strong></td><td><strong>Type</strong></td><td><strong></strong></td></tr>                        
                            {this.state.runningAgents.map(agent => {
                                return <tr>
                                    <td>{agent.name}</td>
                                    <td>{agent.host.address}</td>
                                    <td><em>{agent.type.type}</em></td>
                                    <td><button className="btn" onClick={() => this.stopAgentFunction(agent)}>Stop</button></td>
                                </tr>
                            })}
                        </table>
                    </div>
                    [POST 192.168.0.20:8080/WarAT2020/rest/client/agents/running]
                </div>
            </div>
        );

    }
}

export default Agents;